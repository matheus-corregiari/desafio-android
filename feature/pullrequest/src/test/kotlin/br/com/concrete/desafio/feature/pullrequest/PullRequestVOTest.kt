package br.com.concrete.desafio.feature.pullrequest

import br.com.concrete.desafio.data.model.dto.PullRequestDTO
import br.com.concrete.desafio.data.model.dto.UserDTO
import org.junit.Assert
import org.junit.Test

class PullRequestVOTest {

    @Test
    fun transformPullRequestDTOtoVO_id() {
        val dto = PullRequestDTO(id = 123)
        val vo = PullRequestVO(dto)
        Assert.assertEquals(123, vo.id)
    }

    @Test
    fun transformPullRequestDTOtoVO_title() {
        val dto = PullRequestDTO(title = "ABACATE")
        val vo = PullRequestVO(dto)
        Assert.assertEquals("Abacate", vo.title)
    }

    @Test
    fun transformPullRequestDTOtoVO_description() {
        val dto = PullRequestDTO(body = "ABACATE")
        val vo = PullRequestVO(dto)
        Assert.assertEquals("Abacate", vo.description)
    }

    @Test
    fun transformPullRequestDTOtoVO_description_withNullBody() {
        val dto = PullRequestDTO(body = null)
        val vo = PullRequestVO(dto)
        Assert.assertEquals("", vo.description)
    }

    @Test
    fun transformPullRequestDTOtoVO_avatar() {
        val dto = PullRequestDTO(user = UserDTO(avatarUrl = "url"))
        val vo = PullRequestVO(dto)
        Assert.assertEquals("url", vo.avatar)
    }

    @Test
    fun transformPullRequestDTOtoVO_username() {
        val dto = PullRequestDTO(user = UserDTO(login = "username"))
        val vo = PullRequestVO(dto)
        Assert.assertEquals("Username", vo.username)
    }
}