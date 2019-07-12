package io.github.horaciocome1.reaque.ui.users

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import io.github.horaciocome1.reaque.data.subscriptions.SubscriptionsRepository
import io.github.horaciocome1.reaque.data.topics.Topic
import io.github.horaciocome1.reaque.data.users.User
import io.github.horaciocome1.reaque.data.users.UsersRepository
import io.github.horaciocome1.reaque.util.Constants

class UsersViewModel(
    private val usersRepository: UsersRepository,
    private val subscriptionsRepository: SubscriptionsRepository
) : ViewModel() {

    fun get(parentId: String, requestId: String): LiveData<List<User>> {
        return when (requestId) {
            Constants.SUBSCRIPTIONS_REQUEST -> subscriptionsRepository.getSubscriptions(User(parentId))
            Constants.SUBSCRIBERS_REQUEST -> subscriptionsRepository.getSubscribers(User(parentId))
            else -> usersRepository.get(Topic(parentId))
        }
    }

    fun get(user: User) = usersRepository.get(user)

    fun subscribe(view: View, user: User) {
        view.isEnabled = false
        subscriptionsRepository.subscribe(user) { view.visibility = View.GONE }
    }

    fun unSubscribe(view: View, user: User) {
        view.isEnabled = false
        subscriptionsRepository.unSubscribe(user) { view.visibility = View.GONE }
    }

    fun amSubscribedTo(user: User) = subscriptionsRepository.amSubscribedTo(user)

    fun openSubscribers(view: View, user: User) {
        val directions = UserProfileFragmentDirections.actionOpenUsersFromUserProfile(
            user.id, Constants.SUBSCRIBERS_REQUEST
        )
        view.findNavController().navigate(directions)
    }

    fun openSubscriptions(view: View, user: User) {
        val directions = UserProfileFragmentDirections.actionOpenUsersFromUserProfile(
            user.id, Constants.SUBSCRIPTIONS_REQUEST
        )
        view.findNavController().navigate(directions)
    }

    fun openPosts(view: View, user: User) {
        val directions = UserProfileFragmentDirections.actionOpenPostsFromUserProfile(
            user.id, Constants.USER_POSTS_REQUEST
        )
        view.findNavController().navigate(directions)
    }

}