package seg3x02.employeeGql.resolvers.types

data class CreateEmployeeInput(
    val name: String,
    val dateOfBirth: String,
    val city: String,
    val salary: Float,
    val gender: String? = null,
    val email: String? = null
)

