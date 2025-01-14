package com.example.model

import io.ktor.server.testing.*
import org.junit.Assert.*
import org.junit.Test

class UserRepositoryTest {

    @Test
    fun testFindByRealId() = testApplication {
        val repository = FakeUserRepository()
        val expected = User(4, "david", "098f6bcd4621d373cade4e832627b4f6", "david@example.com", Role.LEADER, false)
        val actual = repository.findById(4)

        assertNotNull("User should be found", actual)
        assertEquals("Users should be equals", expected, actual)
    }

    @Test
    fun testFindByNonExistentId() = testApplication {
        val repository = FakeUserRepository()
        val actual = repository.findById(100)

        assertNull("User should not be found", actual)
    }

    @Test
    fun testFindByRealUsername() = testApplication {
        val repository = FakeUserRepository()
        val expected = User(3,	"charlie","9bf31c7ff062936a96d3c8bd1f1a1a2f", "charlie@example.com", Role.USER, true)
        val actual = repository.findByUsername("charlie")

        assertNotNull("User should be found", actual)
        assertEquals("Users should be equals", expected, actual)
    }

    @Test
    fun testFindByEmptyUsername() = testApplication {
        val repository = FakeUserRepository()
        val actual = repository.findByUsername("")

        assertNull("User should not be found", actual)
    }

    @Test
    fun testFindByNonExistentUsername() = testApplication {
        val repository = FakeUserRepository()
        val actual = repository.findByUsername("test")

        assertNull("User should not be found", actual)
    }

}