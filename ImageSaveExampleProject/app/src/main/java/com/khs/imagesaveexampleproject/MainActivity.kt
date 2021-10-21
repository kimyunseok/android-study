package com.khs.imagesaveexampleproject

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.lifecycle.ViewModelProvider
import com.khs.imagesaveexampleproject.databinding.ActivityMainBinding
import com.khs.imagesaveexampleproject.model.MainViewModel
import java.io.*
import java.lang.Exception
import java.util.jar.Manifest

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

    //이미지 저장 버튼 클릭 메서드
    fun imgSaveOnClick(view: View) {
        val bitmap = drawBitmap()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //Q 버전 이상일 경우. (안드로이드 10, API 29 이상일 경우)
            saveImageOnAboveAndroidQ(bitmap)
            Toast.makeText(baseContext, "이미지 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            // Q 버전 이하일 경우. 저장소 권한을 얻어온다.
            val writePermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if(writePermission == PackageManager.PERMISSION_GRANTED) {
                saveImageOnUnderAndroidQ(bitmap)
                Toast.makeText(baseContext, "이미지 저장이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val requestExternalStorageCode = 1

                val permissionStorage = arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

                ActivityCompat.requestPermissions(this, permissionStorage, requestExternalStorageCode)
            }
        }

    }

    // 화면에 나타난 View를 Bitmap에 그릴 용도.
    private fun drawBitmap(): Bitmap {
        //기기 해상도를 가져옴.
        val backgroundWidth = resources.displayMetrics.widthPixels
        val backgroundHeight = resources.displayMetrics.heightPixels

        val totalBitmap = Bitmap.createBitmap(backgroundWidth, backgroundHeight, Bitmap.Config.ARGB_8888) // 비트맵 생성
        val canvas = Canvas(totalBitmap) // 캔버스에 비트맵을 Mapping.

        val bgColor = binding.viewModel?.background?.value // 뷰모델의 현재 설정된 배경색을 가져온다.
        if(bgColor != null) {
            val color = ContextCompat.getColor(baseContext, bgColor)
            canvas.drawColor(color) // 캔버스에 현재 설정된 배경화면색으로 칠한다.
        }

        val imageView = binding.iv
        val imageViewBitmap = Bitmap.createBitmap(imageView.width, imageView.height, Bitmap.Config.ARGB_8888)
        val imageViewCanvas = Canvas(imageViewBitmap)
        imageView.draw(imageViewCanvas)
        /*imageViewCanvas를 통해서 imageView를 그린다.
         *이 때 스케치북은 imageViewBitmap이므로 imageViewBitmap에 imageView가 그려진다.
         */

        val imageViewLeft = ((backgroundWidth - imageView.width) / 2).toFloat()
        val imageViewTop = ((backgroundHeight - imageView.height) / 2).toFloat()
        /*이미지가 그려질 곳 계산. 정 가운데에 ImageView를 그릴것이다.
        * 기기의 가로크기 - 이미지의 가로크기 를 2로 나눈 후 왼쪽에 해당 크기만큼 마진을 준다.
        * 세로 크기도 마찬가지로 계산해준다.
        * */

        canvas.drawBitmap(imageViewBitmap, imageViewLeft, imageViewTop, null)

        //아래는 TextView. 위에 ImageView와 같은 로직으로 비트맵으로 만든 후 캔버스에 그려준다.
        val textView = binding.tv
        if(textView.length() > 0) {
            //textView가 공백이 아닐때만
            val textViewBitmap = Bitmap.createBitmap(textView.width, textView.height, Bitmap.Config.ARGB_8888)
            val textViewCanvas = Canvas(textViewBitmap)
            textView.draw(textViewCanvas)

            val marginTop = (20 * resources.displayMetrics.density).toInt() // 20dp의 마진
            val textViewLeft = ((backgroundWidth - textView.width) / 2).toFloat()
            val textViewTop = imageViewTop + imageView.height + marginTop

            canvas.drawBitmap(textViewBitmap, textViewLeft, textViewTop, null)
        }

        return totalBitmap
    }

    //Android Q (Android 10, API 29 이상에서는 이 메서드를 통해서 이미지를 저장한다.)
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageOnAboveAndroidQ(bitmap: Bitmap) {
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
    }

    private fun saveImageOnUnderAndroidQ(bitmap: Bitmap) {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val externalStorage = Environment.getExternalStorageDirectory().absolutePath
        val path = "$externalStorage/DCIM/imageSave"
        val dir = File(path)

        if(dir.exists().not()) {
            dir.mkdirs() // 폴더 없을경우 폴더 생성
        }

        try {
            val fileItem = File("$dir/$fileName")
            fileItem.createNewFile()
            //0KB 파일 생성.

            val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            //파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

            fos.close() // 파일 아웃풋 스트림 객체 close

            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
            // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다.
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}