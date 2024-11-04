package com.example.hall9000.ui.usuarios

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hall9000.R
import com.example.hall9000.network.RetrofitClient
import com.example.hall9000.network.UpdateRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditUserFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText = view.findViewById(R.id.nameEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText)
        saveButton = view.findViewById(R.id.saveButton)

        // Carregar dados do usuário a partir do Bundle
        loadUserData()

        saveButton.setOnClickListener {
            updateUser()
        }
    }

    private fun loadUserData() {
        // Obter dados do Bundle
        val userEmail = arguments?.getString("user_email")
        val userName = arguments?.getString("user_name")
        val userSenha = arguments?.getString("user_senha")

        // Definir os dados nos campos de entrada
        nameEditText.setText(userName)
        // Se necessário, você pode usar o userEmail para outros fins, mas não é usado na UI
    }

    private fun updateUser() {
        val userSenha = arguments?.getString("user_senha")
        val newName = nameEditText.text.toString()
        val newPassword = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()
        val userEmail = arguments?.getString("user_email")

        // Validações
        if (newPassword != confirmPassword) {
            Toast.makeText(requireContext(), "As senhas não coincidem.", Toast.LENGTH_SHORT).show()
            return
        }

        // Criar o objeto UpdateRequest
        val updateRequest = if (newPassword.isNotEmpty()) {
            UpdateRequest(email = userEmail ?: "", senha = newPassword, nome = newName)
        } else {
            UpdateRequest(email = userEmail ?: "", senha = userSenha ?: "", nome = newName) // Senha vazia para não alterar
        }

        // Fazer a chamada para a API
        RetrofitClient.apiService.updateUser(updateRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Usuário atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed() // Voltar para a tela anterior
                } else {
                    Toast.makeText(requireContext(), "Erro ao atualizar usuário: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Falha na comunicação com o servidor: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
