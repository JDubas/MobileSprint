package com.example.hall9000.ui.pedidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hall9000.R
import com.example.hall9000.network.PedidoCompleto
import com.example.hall9000.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PedidosFragment : Fragment() {

    private lateinit var nomeTextViews: List<TextView>
    private lateinit var idTextViews: List<TextView>
    private lateinit var dataTextViews: List<TextView>
    private lateinit var valorTextViews: List<TextView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pedidos, container, false)

        // Inicializar as TextViews para cada campo
        nomeTextViews = listOf(
            view.findViewById(R.id.tvPedidoNome1),
            view.findViewById(R.id.tvPedidoNome2),
            view.findViewById(R.id.tvPedidoNome3),
            view.findViewById(R.id.tvPedidoNome4),
            view.findViewById(R.id.tvPedidoNome5)
        )

        idTextViews = listOf(
            view.findViewById(R.id.tvPedidoID1),
            view.findViewById(R.id.tvPedidoID2),
            view.findViewById(R.id.tvPedidoID3),
            view.findViewById(R.id.tvPedidoID4),
            view.findViewById(R.id.tvPedidoID5)
        )

        dataTextViews = listOf(
            view.findViewById(R.id.tvPedidoData1),
            view.findViewById(R.id.tvPedidoData2),
            view.findViewById(R.id.tvPedidoData3),
            view.findViewById(R.id.tvPedidoData4),
            view.findViewById(R.id.tvPedidoData5)
        )

        valorTextViews = listOf(
            view.findViewById(R.id.tvPedidoValor1),
            view.findViewById(R.id.tvPedidoValor2),
            view.findViewById(R.id.tvPedidoValor3),
            view.findViewById(R.id.tvPedidoValor4),
            view.findViewById(R.id.tvPedidoValor5)
        )

        // Chamada à API para preencher os dados
        getPedidos()

        return view
    }

    private fun getPedidos() {
        val call = RetrofitClient.apiService.getPedidos()

        call.enqueue(object : Callback<List<PedidoCompleto>> {
            override fun onResponse(call: Call<List<PedidoCompleto>>, response: Response<List<PedidoCompleto>>) {
                if (response.isSuccessful) {
                    val pedidos = response.body() ?: return

                    // Preencher os TextViews com os dados
                    for (i in pedidos.indices) {
                        nomeTextViews[i].text = "Nome: ${pedidos[i].Nome}"
                        idTextViews[i].text = "ID Pedido: ${pedidos[i].PedidoID}"
                        dataTextViews[i].text = "Data: ${pedidos[i].DataPedido}"
                        valorTextViews[i].text = "Valor: ${pedidos[i].Valor}"
                    }
                }
            }

            override fun onFailure(call: Call<List<PedidoCompleto>>, t: Throwable) {
                // Lidar com o erro
            }
        })
    }
}
