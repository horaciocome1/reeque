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

package io.github.horaciocome1.reaque.data.posts

import com.google.firebase.Timestamp
import io.github.horaciocome1.reaque.data.topics.Topic
import io.github.horaciocome1.reaque.data.users.User
import io.github.horaciocome1.reaque.util.string

data class Post(var id: String) {

    var title = ""
    var date = ""
    var user = User("")
    var pic = ""
    var message = ""
    var topic = Topic("")
    var timestamp = Timestamp.now()
        set(value) {
            date = value.string
            field = value
        }
    var score: Float = 1f - (1f / timestamp.seconds)
    var rating = 0f

}
