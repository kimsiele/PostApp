package com.sielee.postapp

import android.os.Bundle
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

    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostListBinding.inflate(inflater, container,false)
        postAdapter = PostAdapter()
        binding.rvPosts.layoutManager = LinearLayoutManager(context)
        binding.rvPosts.adapter = postAdapter

            GlobalScope.launch {
                val posts = PostApi.apiService.getPosts()
                withContext(Dispatchers.Main) {
                    if (posts.isNotEmpty()) {
                        binding.pbLoading.visibility = View.GONE
                        postAdapter.submitList(posts)
                    } else {
                        Toast.makeText(context, "No post found", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        /*if (requireArguments() == null){
            Toast.makeText(context,"No arguments yet",Toast.LENGTH_SHORT).show()
        }else {
                val argument = requireArguments()
                val newPost = Post(
                    argument.getInt("userId"),
                    argument.getString("title")!!,
                    argument.getString("body")!!
                )
                val newPosts = listOf(newPost)
                postAdapter.submitList(newPosts)
            }*/
        binding.fbPostNew.setOnClickListener {
            this.findNavController().navigate(PostListDirections.actionPostListToPostNew())
        }

        return binding.root
    }

}