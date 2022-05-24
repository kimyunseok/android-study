package com.example.coroutineexampleproject

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.coroutineexampleproject.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.net.URL

/**
 * Coroutine 예제 프로젝트.
 * 기능 :
 * 1. URL을 통해 이미지를 다운로드 받는다.
 * 2. 랜덤 숫자를 텍스트뷰에 나타낸다.
 * 3. 화면을 아래로 당기면 1, 2의 기능을 수행한다.
 *
 * 구현 :
 * lifeCycleScope : 액티비티의 생명주기에 따른 범위를 의미한다. 해당 범위에 벗어나면 코루틴이 중지된다.
 *
 * - URL 클래스를 통해서 이미지의 url에 연결한 후 stream을 생성한다.
 *
 * - getBitmap() 메서드를 통해서 URL에 나와있는 이미지를 BitmapFactory를 통해 decode해서 bitmap의 형태로 만든다.
 * suspendCancellableCoroutine : resume()을 통해 콜백 기능을 제공하며 코루틴이 중지 가능하도록 해준다.
 * 또한 이미지를 다운받는 것은 Dispatchers.IO를 통해 메인 스레드가 아닌 다른 스레드에서 백그라운드로 네트워크 작업을 하도록 한다.
 * 만일 메인 스레드에서 네트워크 작업을 하면, UI 스레드가 멈추므로 ANR(Application Not Responding)이 발생한다.
 *
 * - setImage() 메서드를 통해서 받아온 비트맵을 이미지뷰에 나타낸다.
 * Dispatchers.Main을 통해 메인 스레드(UI 스레드)에서 뷰를 업데이트 하도록 해준다.
 *
 * - getRandomNumber() 메서드를 통해서 랜덤한 숫자를 얻는다.
 * CPU와 관련된 연산처리 작업이므로 Dispatchers.Default를 통해 작업한다.
 *
 * - setText() 메서드를 통해서 받아온 랜덤한 숫자를 텍스트뷰에 나타낸다.
 * Dispatchers.Main을 통해 메인 스레드(UI 스레드)에서 뷰를 업데이트 하도록 해준다.
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val imageURL = "https://picsum.photos/1024/1024"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            setView()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setView() {
        lifecycleScope.launch {
            getBitmap()?.let {
                setImage(it)
            }

            setText(getRandomNumber())
        }
    }

    private suspend fun getBitmap(): Bitmap? = suspendCancellableCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            val inputStream = URL(imageURL).openStream()
            try {
                val bitmap = BitmapFactory.decodeStream(inputStream)
                continuation.resume(bitmap) {}
                Log.d("GET IMAGE", "COMPLETE")
            } catch (e: Exception) {

            } finally {
                inputStream.close()
            }
        }
    }

    private suspend fun setImage(bitmap: Bitmap) =
        withContext(Dispatchers.Main) {
            binding.imageView.setImageBitmap(bitmap)
            Log.d("SET IMAGE", "COMPLETE")
        }

    private suspend fun getRandomNumber(): String = suspendCancellableCoroutine { continuation ->
        CoroutineScope(Dispatchers.Default).launch {
            val randomNum: Long = (Math.random() * 900000000000 + 10000000000).toLong()
            continuation.resume(randomNum.toString()) {}
            Log.d("GET TEXT", "COMPLETE")
        }
    }

    private suspend fun setText(str: String) =
        withContext(Dispatchers.Main) {
            binding.textView.text = str
            Log.d("SET TEXT", "COMPLETE")
        }
}