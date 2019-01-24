/*
 *    Copyright 2018 Horácio Flávio Comé Júnior
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

package io.github.horaciocome1.reaque.ui.comments

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import io.github.horaciocome1.reaque.data.comments.CommentsRepository
import io.github.horaciocome1.reaque.data.posts.Post
import io.github.horaciocome1.reaque.data.topics.Topic
import io.github.horaciocome1.reaque.utilities.InjectorUtils

fun Fragment.getCommentsViewModel(): CommentsViewModel {
    val factory = InjectorUtils.provideCommentsViewModelFactory()
    return ViewModelProviders.of(this, factory)[CommentsViewModel::class.java]
}

class CommentsViewModel(private val commentsRepository: CommentsRepository) : ViewModel() {

    fun getComments(topic: Topic) = commentsRepository.getComments(topic)

    fun getComments(post: Post) = commentsRepository.getComments(post)

}