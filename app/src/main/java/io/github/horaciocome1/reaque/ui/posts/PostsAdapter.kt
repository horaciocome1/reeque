/*
 *    Copyright 2019 Horácio Flávio Comé Júnior
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and limitations under the License.
 */

package io.github.horaciocome1.reaque.ui.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.horaciocome1.reaque.data.posts.Post
import io.github.horaciocome1.reaque.databinding.ItemPostBinding
import io.github.horaciocome1.reaque.databinding.ItemSuggestionBinding

class PostsAdapter(private val list: List<Post>) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private lateinit var binding: ItemPostBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemPostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.post = list[position]
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class SuggestionsAdapter(private val list: List<Post>) :
        RecyclerView.Adapter<SuggestionsAdapter.ViewHolder>() {

        private lateinit var binding: ItemSuggestionBinding

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            binding = ItemSuggestionBinding.inflate(inflater, parent, false)
            return ViewHolder(binding.root)
        }

        override fun getItemCount() = list.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            binding.post = list[position]
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    }

}