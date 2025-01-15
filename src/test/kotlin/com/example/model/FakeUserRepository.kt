package com.example.model

class FakeUserRepository {

    private val users = mutableListOf(
        User(1, "john", "12345678", "john@example.com", Role.USER, false),
        User(2, "alice","5f4dcc3b5aa765d61d8327deb882cf99", "alice@example.com", Role.USER, false),
        User(3,	"charlie","9bf31c7ff062936a96d3c8bd1f1a1a2f", "charlie@example.com", Role.USER, true),
        User(4,	"david", "098f6bcd4621d373cade4e832627b4f6", "david@example.com",	Role.LEADER,	false),
        User(5,	"eve", "c4ca4238a0b923820dcc509a6f75849b", "eve@example.com",	Role.ADMIN,	false),
        User(6,	"frank", "98f13708210194c475687be6106a3b84", "frank@example.com",	Role.USER,	false),
        User(7,	"grace", "b109f3bbbc244eb82441917edf3d6c93", "grace@example.com",	Role.USER,	true),
        User(8,	"hannah", "e4d909c290d0fb1ca068ffaddf22cbd0", "hannah@example.com",	Role.USER,	false),
        User(9,	"ian", "d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2", "ian@example.com",	Role.USER,	true),
        User(10,"jack", "c81e728d9d4c2f636f067f89cc14862c", "jack@example.com",	Role.LEADER,	false)
    )

    fun findAll(): List<User> = users

    fun findById(id: Int): User? = users.find { it.id == id }

    fun findByUsername(username: String): User? = users.find { it.username.equals(username, ignoreCase = true) }

    fun findByEmail(email: String): User? = users.find { it.email.equals(email, ignoreCase = true) }

    fun findByRole(role: Role): List<User> = users.filter { it.role == role }

    fun addUser(user: User) = users.add(user)

    fun updateUser(user: User) {
        if (removeUser(user.id ?: return))
            addUser(user)
    }

    fun removeUser(id: Int): Boolean = users.removeIf { it.id == id }
}