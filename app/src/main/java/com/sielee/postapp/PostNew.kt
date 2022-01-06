package com.sielee.postapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sielee.postapp.databinding.FragmentPostNewBinding
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class PostNew : Fragment() {
    lateinit var binding: FragmentPostNewBinding
    private val TAG = "PostNew"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostNewBinding.inflate(inflater, container, false)


        binding.btnPost.setOnClickListener {
            val newPost = Post(
                binding.edUserId.text.toString().toInt(),
                binding.edTitle.text.toString(),
                binding.edBody.text.toString()
            )
            GlobalScope.launch {
                PostApi.apiService.postValues(newPost)
                val response = PostApi.apiService.postValues(newPost).body()

                Log.d(TAG, "Code: ${response?.id} response: $response ")
                withContext(Dispatchers.Main) {
                    //delay(3000)
                    val postsLists = mutableListOf<Post>()
                    postsLists.add(response!!)

                    findNavController().previousBackStackEntry?.savedStateHandle?.set("response", postsLists)
                    donePosting()
                }

            }
        }
        return binding.root
    }

    private fun donePosting() {
        binding.edUserId.setText("")
        binding.edTitle.setText("")
        binding.edBody.setText("")
        findNavController().popBackStack()
    }
}