package com.makaroni.chocho.features.account.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.makaroni.chocho.R
import com.makaroni.chocho.common.data.ResId
import com.makaroni.chocho.common.data.UiEvent
import com.makaroni.chocho.common.data.UiState
import com.makaroni.chocho.databinding.FragmentAuthBinding
import com.makaroni.chocho.ext.hideProgressBar
import com.makaroni.chocho.ext.showErrorSnackbar
import com.makaroni.chocho.ext.showProgressBar
import com.makaroni.chocho.ext.showSnackbar
import com.makaroni.chocho.utils.observeOnLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val fragmentBinding by viewBinding(FragmentAuthBinding::bind)

    private var googleSignInClient: GoogleSignInClient? = null

    private val viewModel: AuthViewModel by hiltNavGraphViewModels(R.id.navigation_auth)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInWithGoogleInit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
        subscribeObservers()

    }

    private fun subscribeObservers() {
        viewModel.eventsFlow.observeOnLifecycle(this.viewLifecycleOwner) {
            handleUiEvent(it)
        }
        viewModel.uiState.observeOnLifecycle(this.viewLifecycleOwner) {
            handleUiState(it)
        }
    }

    private fun subscribeUi() {
        fragmentBinding.apply {
            etEmail.setText(viewModel.email)
            etPassword.setText(viewModel.password)

            btnGoogleSign.setOnClickListener { signInWithGoogle() }
            btnSignUp.setOnClickListener { signUpWithEmailPassword() }
            etEmail.doOnTextChanged { text, _, _, _ ->
                ltEmail.error = null
                viewModel.email = text.toString()
            }
            etPassword.doOnTextChanged { text, _, _, _ ->
                ltPassword.error = null
                viewModel.password = text.toString()
            }
        }
    }

    private fun navigateTo(destination: ResId) {
        val navController = findNavController()
        when (destination.id) {
            R.id.authVerificationFragment -> navController.navigate(AuthFragmentDirections.actionAuthFragmentToAuthVerificationFragment())
        }
    }

    private fun handleUiState(state: UiState<Nothing>) {
        when (state) {
//            is UiState.Error -> TODO()
            UiState.Idle -> hideProgressBar()
            UiState.Loading -> showProgressBar()
//            is UiState.Success -> TODO()
        }
    }

    private fun handleUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.NavigateTo -> navigateTo(event.id)
            is UiEvent.Error -> showErrorSnackbar(fragmentBinding.root, event)
            is UiEvent.ShowSnackbar -> showSnackbar(fragmentBinding.root, event)
        }
    }

    private fun signInWithGoogleInit() {
        val googleSingInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("331283844427-7cirgci61kpfqjqt4h6eqtbnffbhutvi.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSingInOptions)
    }

    private fun signUpWithEmailPassword() {
        val isPasswordValid = viewModel.validatePassword()
        val isEmailValid = viewModel.validateEmail()
        val isFieldsValid = isEmailValid && isPasswordValid

        if (!isPasswordValid) {
            fragmentBinding.ltPassword.error = getString(R.string.auth_error_password)
        }

        if (!isEmailValid) {
            fragmentBinding.ltEmail.error = getString(R.string.auth_error_email)
        }

        if (isFieldsValid) {
            viewModel.signUpWithEmailPassword()
        }
    }

    private fun signInWithGoogle() {
        openGoogleAuthActivity.launch(googleSignInClient?.signInIntent)
    }

    private fun handleGoogleAuth(account: GoogleSignInAccount) {
        val googleTokenId = account.idToken
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
        viewModel.signInWithGoogle(googleAuthCredential)
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
                Log.e(TAG, e.message, e)
            }
        }

    companion object {
        val TAG = AuthFragment::class.java.simpleName
    }
}