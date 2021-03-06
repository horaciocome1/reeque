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

package io.github.horaciocome1.reaque.data.storage

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.github.horaciocome1.reaque.data.topics.Topic

class StorageService {

    private val storage: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    fun upload(imageUri: Uri, topic: Topic, onSuccessListener: (Uri?) -> Unit) {
        if (
            imageUri != Uri.EMPTY
            && topic.id.isNotBlank()
        ) {
            val path = "images/${topic.id}/${imageUri.lastPathSegment}"
            val ref = storage.reference.child(path)
            ref.putFile(imageUri)
                .continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful)
                        task.exception?.let {
                            throw it
                        }
                    return@Continuation ref.downloadUrl
                })
                .addOnSuccessListener(onSuccessListener)
        }
    }

}