package br.com.fiap.service

import br.com.fiap.entity.User
import br.com.fiap.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    fun findById(id: String): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun findByLoginAndPassword(login: String, password: String): User? {
        return userRepository.findByLoginAndPassword(login, password)
    }

    fun register(user:User) {
        userRepository.save(user)
    }

    fun notifyTripChangeStatus(user: User, toStatus: String) {
        System.out.println("-----Sending push notification change of status from trip to user-----")

        //simulating a  time to push notification the user
        TimeUnit.SECONDS.sleep(1);

        System.out.println("-----Sended-----")
    }

    fun requestPayment(user: User, value: Double): Boolean {
        System.out.println("-----Requesting payment to operator-----")

        //simulating a  time to handle payment
        TimeUnit.SECONDS.sleep(1);

        System.out.println("-----Payed-----")
        return true
    }
}