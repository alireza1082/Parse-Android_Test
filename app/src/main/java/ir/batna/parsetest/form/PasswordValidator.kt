package ir.batna.parsetest.form

class PasswordValidator {
    data class PasswordValidationState(
        val hasMinimum: Boolean = false,
        val hasCapitalizedLetter: Boolean = false,
        val hasSpecialCharacter: Boolean = false,
        val successful: Boolean = false
    )

    fun execute(password: String): PasswordValidationState {

        val hasSpecialCharacter = validateSpecialCharacter(password)
        val hasCapitalizedLetter = validateCapitalizedLetter(password)
        val hasMinimum = validateMinimum(password)

        val hasError = listOf(
            hasSpecialCharacter,
            hasCapitalizedLetter,
            hasMinimum
        ).all { it }

        return PasswordValidationState(
            hasSpecialCharacter = hasSpecialCharacter,
            hasCapitalizedLetter = hasCapitalizedLetter,
            hasMinimum = hasMinimum,
            successful = hasError
        )
    }

    private fun validateSpecialCharacter(password: String): Boolean =
        password.matches(Regex(".*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"))

    private fun validateCapitalizedLetter(password: String): Boolean =
        password.matches(Regex(".*[A-Z].*"))

    private fun validateMinimum(password: String): Boolean =
        password.matches(Regex(".{6,}"))
}