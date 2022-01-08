package com.example.anggorobenolukito.utils

import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.data.remote.response.DetailUserResponse
import com.example.anggorobenolukito.domain.DetailUserModel

object DataMapper {
    fun mapDetailUserEntitiesToModel(data: DetailUserEntity): DetailUserModel {
        val user = DetailUserModel(
            data.isFavourite,
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
        return user
    }

    fun mapUserEntitiesToModel2(data: DetailUserEntity): List<DetailUserModel> {
        val users = ArrayList<DetailUserModel>()
        val user = DetailUserModel(
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