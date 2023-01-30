package uz.digital.volleyfull

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import uz.digital.volleyfull.adapter.PostAdapter
import uz.digital.volleyfull.databinding.FragmentFirstBinding
import uz.digital.volleyfull.model.Post
import uz.digital.volleyfull.network.ResponseHandler
import uz.digital.volleyfull.network.VolleyInstance
import uz.digital.volleyfull.util.toast

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val volleyInstance by lazy { VolleyInstance() }
    private val postAdapter by lazy { PostAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }
        volleyInstance.get(object : ResponseHandler {
            override fun onResponse(string: String) {
                val postList = Gson().fromJson(string, Array<Post>::class.java)
                postAdapter.submitList(postList.toMutableList())
                binding.progressBar.isVisible = false
                println("@@@$postList")
            }

            override fun onError(string: String?) {
                toast(string)
            }
        })
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        postAdapter.onClick = {
            val bundle = bundleOf("id" to it)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }
        postAdapter.onLongClick = {
            volleyInstance.delete(it, object : ResponseHandler {
                override fun onResponse(string: String) {
                    toast("Deleted")
                }

                override fun onError(string: String?) {
                    toast(string)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}