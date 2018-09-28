package br.com.concrete.desafio.feature.pullrequest

import br.com.concrete.desafio.data.model.dto.PullRequestDTO

class PullRequestVO(pullRequestDTO: PullRequestDTO) {

    val id = pullRequestDTO.id
    val title = pullRequestDTO.title.toLowerCase().capitalize()
    val description = pullRequestDTO.body?.toLowerCase()?.capitalize() ?: ""
    val avatar = pullRequestDTO.user.avatarUrl
    val username = pullRequestDTO.user.login.capitalize()
}