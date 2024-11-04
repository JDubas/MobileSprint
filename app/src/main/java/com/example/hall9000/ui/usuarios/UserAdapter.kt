import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hall9000.R
import com.example.hall9000.network.LoginResponse

class UserAdapter(
    private val users: List<LoginResponse>,
    private val listener: OnUserActionListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user, listener)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUserNome: TextView = itemView.findViewById(R.id.tvUserNome)
        private val tvUserID: TextView = itemView.findViewById(R.id.tvUserID)
        private val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        private val btnDeletar: Button = itemView.findViewById(R.id.btnDeletar)

        fun bind(user: LoginResponse, listener: OnUserActionListener) {
            tvUserNome.text = "Nome: ${user.nome}"
            tvUserID.text = "Email: ${user.email}"

            btnEditar.setOnClickListener { listener.onEdit(user) }
            btnDeletar.setOnClickListener { listener.onDelete(user) }
        }
    }

    interface OnUserActionListener {
        fun onEdit(user: LoginResponse)
        fun onDelete(user: LoginResponse)
    }
}
