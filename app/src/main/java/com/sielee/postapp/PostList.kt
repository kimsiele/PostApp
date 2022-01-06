package com.sielee.postapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sielee.postapp.databinding.FragmentPostListBinding
import kotlinx.coroutines.*

class PostList : Fragment() {

    lateinit var binding: FragmentPostListBinding
    private lateinit var postAdapter: PostAdapter
    val TAG = "PostList"

    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostListBinding.inflate(inflater, container, false)
        postAdapter = PostAdapter()
        binding.rvPosts.layoutManager = LinearLayoutManager(context)
        binding.rvPosts.adapter = postAdapter

        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<MutableList<Post>>("response")?.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Toast.makeText(context, "New post", Toast.LENGTH_LONG).show()
                    binding.pbLoading.visibility = View.GONE
                    postAdapter.submitList(it)
                    Log.d(TAG, "ResponseList: $it")
                }
            }
        GlobalScope.launch {
            val posts = PostApi.apiService.getPosts()
            withContext(Dispatchers.Main) {
                if (posts.isNotEmpty()) {
                    binding.pbLoading.visibility = View.GONE
                    if (postAdapter.currentList.isEmpty()) {
                        postAdapter.submitList(posts)
                        Toast.makeText(context, "Posts from api", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, "No post yet!", Toast.LENGTH_LONG).show()
                }
            }

        }


        binding.fbPostNew.setOnClickListener {
            this.findNavController().navigate(PostListDirections.actionPostListToPostNew())
        }
        return binding.root
    }

}