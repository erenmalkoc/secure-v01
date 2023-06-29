package com.malkoceren.chatappkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.malkoceren.chatappkotlin.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(auth.currentUser!=null){
            val action = LoginFragmentDirections.actionLoginFragmentToChatFragment()
            findNavController().navigate(action)

        }
        binding.signUpButton.setOnClickListener {
            //kayıt

            auth.createUserWithEmailAndPassword(
                binding.emailText.text.toString(),
                binding.passwordText.text.toString()
            )
                .addOnSuccessListener {
                    //kullanıcı oluşturuldu
                    val action = LoginFragmentDirections.actionLoginFragmentToChatFragment()
                    findNavController().navigate(action)


                }.addOnFailureListener { exception ->
                    //hata çıktısı
                    Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }


        }

        binding.loginButton.setOnClickListener {
            //giriş
            auth.signInWithEmailAndPassword(binding.emailText.text.toString(),
                binding.passwordText.text.toString()).addOnSuccessListener {
                val action = LoginFragmentDirections.actionLoginFragmentToChatFragment()
                findNavController().navigate(action)

            }.addOnFailureListener{
                Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}