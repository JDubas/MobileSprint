package com.example.hall9000.ui.usuarios

import UserAdapter
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hall9000.R
import com.example.hall9000.network.DeleteRequest
import com.example.hall9000.network.LoginResponse
import com.example.hall9000.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListFragment : Fragment(), UserAdapter.OnUserActionListener {

    private lateinit var userAdapter: UserAdapter
    private val users = mutableListOf<LoginResponse>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        userAdapter = UserAdapter(users, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = userAdapter

        fetchUsers() // Carregar os usuários

        return view
    }

    private fun fetchUsers() {
        // Utilize o apiService do RetrofitClient
        RetrofitClient.apiService.getLogins().enqueue(object : Callback<List<LoginResponse>> {
            override fun onResponse(call: Call<List<LoginResponse>>, response: Response<List<LoginResponse>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        users.clear() // Limpa a lista antes de adicionar os novos usuários
                        users.addAll(it)
                        userAdapter.notifyDataSetChanged() // Notifica o adapter para atualizar a lista
                    }
                } else {
                    Toast.makeText(requireContext(), "Erro ao obter usuários: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<LoginResponse>>, t: Throwable) {
                Toast.makeText(requireContext(), "Erro na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onEdit(user: LoginResponse) {
        // Cria um Bundle para passar os dados do usuário para o EditUserFragment
        val bundle = Bundle().apply {
            putString("user_email", user.email) // Adicione outros dados do usuário se necessário
            putString("user_name", user.nome) // Supondo que LoginResponse tenha um campo 'name'
            putString("user_senha", user.senha)
        }

        // Navegar para o EditUserFragment com os dados do usuário
        findNavController().navigate(R.id.navigation_usuarios_edit, bundle)
    }




    override fun onDelete(user: LoginResponse) {
        // Exibir um popup de confirmação antes de deletar o usuário
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmação")
            .setMessage("Você tem certeza que deseja deletar o usuário ${user.email}?")
            .setPositiveButton("Sim") { dialog, which ->
                // Chamar a função de deletar usuário, passando o email
                RetrofitClient.apiService.deleteUser(user.email).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            // Usuário deletado com sucesso
                            users.remove(user)
                            userAdapter.notifyDataSetChanged()
                            Toast.makeText(requireContext(), "Usuário deletado com sucesso!", Toast.LENGTH_SHORT).show()
                        } else {
                            // Tratar erro na resposta
                            Toast.makeText(requireContext(), "Erro ao deletar usuário.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        // Tratar erro na chamada
                        Toast.makeText(requireContext(), "Falha na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            .setNegativeButton("Não") { dialog, which -> dialog.dismiss() }
            .show()
    }













}
