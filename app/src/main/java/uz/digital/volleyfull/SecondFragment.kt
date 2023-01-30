package uz.digital.volleyfull

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import uz.digital.volleyfull.databinding.FragmentSecondBinding
import uz.digital.volleyfull.model.Post
import uz.digital.volleyfull.network.ResponseHandler
import uz.digital.volleyfull.network.VolleyInstance
import uz.digital.volleyfull.util.toast

class SecondFragment : Fragment() {

    private var id: Int? = null

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val volleyInstance by lazy { VolleyInstance() }
    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (id != null) {
            binding.btnPost.text = "Update"
            volleyInstance.getById(id!!, object : ResponseHandler {
                override fun onResponse(string: String) {
                    post = Gson().fromJson(string, Post::class.java)
                    binding.apply {
                        pr.isVisible = true
                        title.setText(post.title)
                        body.setText(post.body)
                    }
                }

                override fun onError(string: String?) {
                    toast(string)
                    binding.pr.isVisible = false
                }
            })
        }

        binding.btnPost.setOnClickListener {
            val title = binding.title.text.toString().trim()
            val body = binding.body.text.toString().trim()
            if (title.isNotBlank() && body.isNotBlank()) {
                binding.pr.isVisible = true
                binding.btnPost.isVisible = false
                if (id != null) {
                    updatePost(title, body)
                } else {
                    postData(title, body)
                }
            } else {
                toast("Enter data")
            }
        }
    }

    private fun updatePost(title: String, body: String) {
        volleyInstance.put(Post(0, 0, title, body), object : ResponseHandler {
            override fun onResponse(string: String) {
                binding.pr.isVisible = false
                binding.btnPost.isVisible = true
                toast("Updated")
            }

            override fun onError(string: String?) {
                toast(string)
                binding.pr.isVisible = false
                binding.btnPost.isVisible = true
            }
        })
    }

    private fun postData(title: String, body: String) {
        volleyInstance.post(Post(0, 0, title, body), object : ResponseHandler {
            override fun onResponse(string: String) {
                Log.d("@@@", "onResponse: $string")
                binding.pr.isVisible = false
                binding.btnPost.isVisible = true
                toast("Created")
            }

            override fun onError(string: String?) {
                toast(string)
                binding.pr.isVisible = false
                binding.btnPost.isVisible = true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}