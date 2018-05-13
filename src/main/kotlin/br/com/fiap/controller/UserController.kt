package br.com.fiap.controller

import br.com.fiap.service.UserService
import br.com.fiap.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/login")
    fun login(@RequestBody user: User): User? {
        return userService.findByLoginAndPassword(user.login, user.password)
    }

    @PostMapping
    fun save(@RequestBody user: User) {
        userService.register(user)
    }
}