package com.logicsoul.mvp.view.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.logicsoul.mvp.R
import com.logicsoul.mvp.databinding.UserCardLayoutBinding
import com.logicsoul.mvp.model.User

/**
 * User adapter
 */
class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    var itemList = listOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        DataBindingUtil.inflate<UserCardLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.user_card_layout,
            parent,
            false
        ).let {
            ViewHolder(it)
        }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(itemList[position])

    /**
     * View holder class
     */
    class ViewHolder(private val binding: UserCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {
            binding.apply {
                user = item
                executePendingBindings()
            }
        }
    }

    fun setUsers(users: List<User>) {
        this.itemList = users
        notifyDataSetChanged()
    }
}