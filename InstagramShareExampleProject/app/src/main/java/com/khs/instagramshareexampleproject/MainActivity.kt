package com.khs.instagramshareexampleproject

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.khs.instagramshareexampleproject.databinding.ActivityMainBinding
import com.khs.instagramshareexampleproject.model.MainViewModel
import java.io.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        initViewModel()
        setUpLifeCycleOwner()

        setContentView(binding.root)
    }

    private fun initViewModel() {
        // 뷰모델 초기화 메서드
        binding.viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun setUpLifeCycleOwner() {
        binding.lifecycleOwner = this
        /*
        * LiveData에서는 LifeCycleOwner만 지정해주면
        * invalidateAll() 메서드를호출하지 않아도
        * DataBinding에서 ViewModel의 값 변동을 감지하고 View Update를 해준다.
        * */
    }

    fun instaShareBtn(view: View) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val bgBitmap = drawBackgroundBitmap()
            val bgUri = saveImageAtCacheDir(bgBitmap)

            val viewBitmap = drawViewBitmap()
            val viewUri = saveImageAtCacheDir(viewBitmap)

            instaShare(bgUri, viewUri)
        } else {
            val bgBitmap = drawBackgroundBitmap()
            val bgUri = saveImageAtCacheDir(bgBitmap)

            val viewBitmap = drawViewBitmap()
            val viewUri = saveImageAtCacheDir(viewBitmap)

            instaShare(bgUri, viewUri)

            // Q 버전 이하일 경우. 저장소 권한을 얻어온다.
//            val writePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//
//            if(writePermission == PackageManager.PERMISSION_GRANTED) {
//                val bgBitmap = drawBackgroundBitmap()
//                val bgUri = saveImageAtCacheDir(bgBitmap)
//
//                val viewBitmap = drawViewBitmap()
//                val viewUri = saveImageAtCacheDir(viewBitmap)
//
//                instaShare(bgUri, viewUri)
//            } else {
//                val requestExternalStorageCode = 1
//
//                val permissionStorage = arrayOf(
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                )
//
//                ActivityCompat.requestPermissions(this, permissionStorage, requestExternalStorageCode)
//            }
        }
    }

    fun instaShare(bgUri: Uri?, viewUri: Uri?) {
// Define image asset URI
        val stickerAssetUri = Uri.parse(viewUri.toString())
        val sourceApplication = "com.khs.instagramshareexampleproject"

// Instantiate implicit intent with ADD_TO_STORY action,
// sticker asset, and background colors
        val intent = Intent("com.instagram.share.ADD_TO_STORY")
        intent.putExtra("source_application", sourceApplication)

        intent.type = "image/png"
        intent.setDataAndType(bgUri, "image/png");
        intent.putExtra("interactive_asset_uri", stickerAssetUri)

// Instantiate activity and verify it will resolve implicit intent
        grantUriPermission(
            "com.instagram.android", stickerAssetUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        grantUriPermission(
            "com.instagram.android", bgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        try {
            this.startActivity(intent)
        } catch (e : ActivityNotFoundException) {
            Toast.makeText(applicationContext, "인스타그램 앱이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
//        try{
//            //저장해놓고 삭제한다.
//            Thread.sleep(1000)
//            viewUri?.let { uri -> contentResolver.delete(uri, null, null) }
//            bgUri?.let { uri -> contentResolver.delete(uri, null, null) }
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
    }

    // 화면에 나타난 View를 Bitmap에 그릴 용도.
    private fun drawBackgroundBitmap(): Bitmap {
        //기기 해상도를 가져옴.
        val backgroundWidth = resources.displayMetrics.widthPixels
        val backgroundHeight = resources.displayMetrics.heightPixels

        val backgroundBitmap = Bitmap.createBitmap(backgroundWidth, backgroundHeight, Bitmap.Config.ARGB_8888) // 비트맵 생성
        val canvas = Canvas(backgroundBitmap) // 캔버스에 비트맵을 Mapping.

        val bgColor = binding.viewModel?.background?.value // 뷰모델의 현재 설정된 배경색을 가져온다.
        if(bgColor != null) {
            val color = ContextCompat.getColor(baseContext, bgColor)
            canvas.drawColor(color) // 캔버스에 현재 설정된 배경화면색으로 칠한다.
        }

        return backgroundBitmap
    }

    private fun drawViewBitmap(): Bitmap {
        val imageView = binding.iv
        val textView = binding.tv

        val margin = resources.displayMetrics.density * 20

        val width = if (imageView.width > textView.width) {
            imageView.width
        } else {
            textView.width
        }

        val height = (imageView.height + textView.height + margin).toInt()

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val imageViewBitmap = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
        val imageViewCanvas = Canvas(imageViewBitmap)
        imageView.draw(imageViewCanvas)
        /*imageViewCanvas를 통해서 imageView를 그린다.
         *이 때 스케치북은 imageViewBitmap이므로 imageViewBitmap에 imageView가 그려진다.
         */

        val imageViewLeft = ((width - imageView.width) / 2).toFloat()

        canvas.drawBitmap(imageViewBitmap, imageViewLeft, (0).toFloat(), null)

        //아래는 TextView. 위에 ImageView와 같은 로직으로 비트맵으로 만든 후 캔버스에 그려준다.
        if(textView.length() > 0) {
            //textView가 공백이 아닐때만
            val textViewBitmap = Bitmap.createBitmap(textView.width, textView.height, Bitmap.Config.ARGB_8888)
            val textViewCanvas = Canvas(textViewBitmap)
            textView.draw(textViewCanvas)

            val textViewLeft = ((width - textView.width) / 2).toFloat()
            val textViewTop = imageView.height + margin

            canvas.drawBitmap(textViewBitmap, textViewLeft, textViewTop, null)
        }

        return bitmap
    }

    //Android Q (Android 10, API 29 이상에서는 이 메서드를 통해서 이미지를 저장한다.)
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageOnAboveAndroidQ(bitmap: Bitmap): Uri? {
        val fileName = System.currentTimeMillis().toString() + ".png" // 파일이름 현재시간.png

        /*
        * ContentValues() 객체 생성.
        * ContentValues는 ContentResolver가 처리할 수 있는 값을 저장해둘 목적으로 사용된다.
        * */
        val contentValues = ContentValues()
        contentValues.apply {
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/ImageSave") // 경로 설정
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName) // 파일이름을 put해준다.
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.IS_PENDING, 1) // 현재 is_pending 상태임을 만들어준다.
            // 다른 곳에서 이 데이터를 요구하면 무시하라는 의미로, 해당 저장소를 독점할 수 있다.
        }

        // 이미지를 저장할 uri를 미리 설정해놓는다.
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            if(uri != null) {
                val image = contentResolver.openFileDescriptor(uri, "w", null)
                // write 모드로 file을 open한다.

                if(image != null) {
                    val fos = FileOutputStream(image.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    //비트맵을 FileOutputStream를 통해 compress한다.
                    fos.close()

                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0) // 저장소 독점을 해제한다.
                    contentResolver.update(uri, contentValues, null, null)
                }
            }
        } catch(e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return uri
    }

    // Android Q 미만에서 파일 저장 후 Uri 반환해주는 메서드
    private fun saveImageOnUnderAndroidQ(bitmap: Bitmap): Uri? {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val externalStorage = Environment.getExternalStorageDirectory().absolutePath
        val path = "$externalStorage/DCIM/imageSave"
        val dir = File(path)

        if(dir.exists().not()) {
            dir.mkdirs() // 폴더 없을경우 폴더 생성
        }

        val fileItem = File("$dir/$fileName")
        try {
            fileItem.createNewFile()
            //0KB 파일 생성.

            val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            //파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

            fos.close() // 파일 아웃풋 스트림 객체 close

            MediaScannerConnection.scanFile(applicationContext, arrayOf(fileItem.toString()), null, null)

            //sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
            // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다. - Deprecated 위 코드로 수정
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return FileProvider.getUriForFile(applicationContext, "com.khs.instagramshareexampleproject.fileprovider", fileItem)
    }

    // 이미지를 캐시에 저장하는 메서드. Android 버전과 상관 없다.
    private fun saveImageAtCacheDir(bitmap: Bitmap): Uri? {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val cachePath = "$cacheDir/file"
        val dir = File(cachePath)

        if(dir.exists().not()) {
            dir.mkdirs() // 폴더 없을경우 폴더 생성
        }

        val fileItem = File("$dir/$fileName")
        try {
            fileItem.createNewFile()
            //0KB 파일 생성.

            val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            //파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

            fos.close() // 파일 아웃풋 스트림 객체 close

            MediaScannerConnection.scanFile(applicationContext, arrayOf(fileItem.toString()), null, null)

            //sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
            // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다. - Deprecated 위 코드로 수정
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return FileProvider.getUriForFile(applicationContext, "com.khs.instagramshareexampleproject.fileprovider", fileItem)
    }
}