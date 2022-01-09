package com.example.anggorobenolukito.core.utils

import com.example.anggorobenolukito.core.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.core.data.remote.response.DetailUserResponse
import com.example.anggorobenolukito.core.data.remote.response.UserResult
import com.example.anggorobenolukito.domain.model.UserModel

object DataMapper {

    fun mapUserResponseToModel(data: List<UserResult>): List<UserModel> {
        return data.map {
            with(it) {
                UserModel(
                    reposUrl,
                    gistsUrl,
                    followingUrl,
                    starredUrl,
                    login,
                    followersUrl,
                    type,
                    url,
                    subscriptionsUrl,
                    score,
                    avatarUrl,
                    id
                )

            }

        }
    }


    fun mapDetailResponseToEntities(data: DetailUserResponse): List<DetailUserEntity> {
        val users = ArrayList<DetailUserEntity>()
        val user = DetailUserEntity(
            false,
            data.gistsUrl,
            data.reposUrl,
            data.followingUrl,
            data.twitterUsername,
            data.bio,
            data.createdAt,
            data.login,
            data.type,
            data.blog,
            data.subscriptionsUrl,
            data.updatedAt,
            data.siteAdmin,
            data.company,
            data.id,
            data.publicRepos,
            data.gravatarId,
            data.email,
            data.organizationsUrl,
            data.hireable,
            data.starredUrl,
            data.followersUrl,
            data.publicGists,
            data.followersUrl,
            data.receivedEventsUrl,
            data.followers,
            data.avatarUrl,
            data.receivedEventsUrl,
            data.htmlUrl,
            data.following,
            data.name,
            data.location,
            data.nodeId
        )
        users.add(user)
        return users


    }

}