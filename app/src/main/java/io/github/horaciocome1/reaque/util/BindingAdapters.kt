package io.github.horaciocome1.reaque.util

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import io.github.horaciocome1.reaque.data.posts.Post
import io.github.horaciocome1.reaque.data.topics.Topic
import io.github.horaciocome1.reaque.data.users.User
import io.github.horaciocome1.reaque.ui.explore.ExploreFragmentDirections
import io.github.horaciocome1.reaque.ui.explore.TopicsAdapter
import io.github.horaciocome1.reaque.ui.feed.FeedFragmentDirections
import io.github.horaciocome1.reaque.ui.posts.PostsAdapter
import io.github.horaciocome1.reaque.ui.posts.PostsFragmentDirections
import io.github.horaciocome1.reaque.ui.users.UsersAdapter
import io.github.horaciocome1.reaque.ui.users.UsersFragmentDirections
import io.github.horaciocome1.simplerecyclerviewtouchlistener.addOnItemClickListener
import io.github.horaciocome1.simplerecyclerviewtouchlistener.addOnItemLongPressListener
import jp.wasabeef.glide.transformations.BlurTransformation

class BindingAdapters {

    class LoadImageFromUrlOrUri {

        companion object {

            @BindingAdapter("url", "uri", "type", requireAll = false)
            @JvmStatic
            fun ImageView.loadImage(url: String?, uri: Uri?, type: Int?) {
                Glide.with(context)
                    .load(
                        if (uri != null && uri != Uri.EMPTY)
                            uri
                        else
                            url
                    )
                    .apply(
                        when (type) {
                            Constants.BLUR -> RequestOptions.bitmapTransform(BlurTransformation(7, 14))
                            Constants.CIRCLE -> RequestOptions.circleCropTransform()
                            else -> RequestOptions()
                        }
                    )
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(this)
            }

        }

    }

    @Suppress("UNCHECKED_CAST")
    class LoadList {

        companion object {

            @BindingAdapter("list", "type", "columns", "host")
            @JvmStatic
            fun RecyclerView.loadList(l: List<Any>?, t: Int?, c: Int?, h: Int?) {
                t?.let { type ->
                    l?.let { list ->
                        c?.let { columns ->
                            h?.let { host ->
                                when (type) {
                                    Constants.LISTING_TOPICS -> {
                                        val topics = list as List<Topic>
                                        loadTopics(topics, columns, host)
                                    }
                                    Constants.LISTING_POSTS -> {
                                        val posts = list as List<Post>
                                        loadPosts(posts, columns, host)
                                    }
                                    Constants.LISTING_POSTS_ON_SUGGESTIONS -> {
                                        val posts = list as List<Post>
                                        loadPostsOnSuggestions(posts, host)
                                    }
                                    Constants.LISTING_USERS -> {
                                        val users = list as List<User>
                                        loadUsers(users, columns)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            private fun RecyclerView.loadTopics(list: List<Topic>, columns: Int, host: Int) {
                layoutManager = if (columns == 1)
                    LinearLayoutManager(context)
                else
                    StaggeredGridLayoutManager(columns, RecyclerView.VERTICAL)
                adapter = TopicsAdapter(list)
                if (host == Constants.EXPLORE_FRAGMENT) {
                    addOnItemClickListener { view, position ->
                        val directions = ExploreFragmentDirections.actionOpenPostsFromExplore(
                            list[position].id, Constants.TOPIC_POSTS_REQUEST
                        )
                        view.findNavController().navigate(directions)
                    }
                    addOnItemLongPressListener { view, position ->
                        val directions = ExploreFragmentDirections.actionOpenUsersFromExplore(
                            list[position].id, Constants.TOPIC_USERS_REQUEST
                        )
                        view.findNavController().navigate(directions)
                    }
                }
            }

            private fun RecyclerView.loadPosts(list: List<Post>, columns: Int, host: Int) {
                layoutManager = if (columns == 1)
                    LinearLayoutManager(context)
                else
                    StaggeredGridLayoutManager(columns, RecyclerView.VERTICAL)
                adapter = PostsAdapter(list)
                addOnItemClickListener { view, position ->
                    val directions = when (host) {
                        Constants.FEED_FRAGMENT -> FeedFragmentDirections.actionOpenReadPostFromFeed(list[position].id)
                        else -> PostsFragmentDirections.actionOpenReadPostFromPosts(list[position].id)
                    }
                    view.findNavController().navigate(directions)
                }
            }

            private fun RecyclerView.loadPostsOnSuggestions(list: List<Post>, host: Int) {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = PostsAdapter.SuggestionsAdapter(list)
                if (host == Constants.EXPLORE_FRAGMENT)
                    addOnItemClickListener { view, position ->
                        val directions = ExploreFragmentDirections.actionOpenReadPostFromExplore(list[position].id)
                        view.findNavController().navigate(directions)
                    }
            }

            private fun RecyclerView.loadUsers(list: List<User>, columns: Int) {
                layoutManager = if (columns == 1)
                    LinearLayoutManager(context)
                else
                    StaggeredGridLayoutManager(columns, RecyclerView.VERTICAL)
                adapter = UsersAdapter(list)
                addOnItemClickListener { view, position ->
                    val directions = UsersFragmentDirections.actionOpenUserProfileFromUsers(list[position].id)
                    view.findNavController().navigate(directions)
                }
            }

        }

    }

}