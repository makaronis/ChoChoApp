package com.makaroni.chocho.features.account.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.makaroni.chocho.R
import com.makaroni.chocho.databinding.FragmentAuthBinding

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val binding by viewBinding(FragmentAuthBinding::bind)

    private val viewModel: AuthViewModel by hiltNavGraphViewModels(R.id.navigation_auth)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
        subscribeObservers()
    }

    private fun subscribeObservers() {

    }

    private fun subscribeUi() {
        binding.apply {
            btnLogin.setOnClickListener { navigateToLogin() }
            btnSignUp.setOnClickListener { navigateToSignUp() }
            btnGoogle.setOnClickListener { signInWithGoogle() }
        }
    }

    private fun signInWithGoogle() {
        Log.d("TAG","signInWithGoogle")
        openGoogleAuthActivity.launch(viewModel.googleSignInClient.signInIntent)
    }

    private val openGoogleAuthActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val googleSignInAccount = task.getResult(ApiException::class.java)
                if (googleSignInAccount != null) {
                    handleGoogleAuth(googleSignInAccount)
                }
            } catch (e: ApiException) {
                Log.e(AuthSignUpFragment.TAG, e.message, e)
            }
        }

    private fun handleGoogleAuth(account: GoogleSignInAccount) {
        val googleTokenId = account.idToken
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
        viewModel.signInWithGoogle(googleAuthCredential)
    }

    private fun navigateTo() {

    }

    private fun navigateToSignUp() {
        findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToAuthFragmentSignUp())
    }

    private fun navigateToLogin() {
        findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToAuthSignInFragment())
    }
}