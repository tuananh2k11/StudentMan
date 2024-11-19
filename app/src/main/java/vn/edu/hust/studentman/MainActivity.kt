package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

  private lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val students = mutableListOf(
      StudentModel("Nguyễn Văn An", "SV001"),
      StudentModel("Trần Thị Bảo", "SV002"),
      StudentModel("Lê Hoàng Cường", "SV003"),
      StudentModel("Phạm Thị Dung", "SV004"),
      StudentModel("Đỗ Minh Đức", "SV005"),
      StudentModel("Vũ Thị Hoa", "SV006"),
      StudentModel("Hoàng Văn Hải", "SV007"),
      StudentModel("Bùi Thị Hạnh", "SV008"),
      StudentModel("Đinh Văn Hùng", "SV009"),
      StudentModel("Nguyễn Thị Linh", "SV010"),
      StudentModel("Phạm Văn Long", "SV011"),
      StudentModel("Trần Thị Mai", "SV012"),
      StudentModel("Lê Thị Ngọc", "SV013"),
      StudentModel("Vũ Văn Nam", "SV014"),
      StudentModel("Hoàng Thị Phương", "SV015"),
      StudentModel("Đỗ Văn Quân", "SV016"),
      StudentModel("Nguyễn Thị Thu", "SV017"),
      StudentModel("Trần Văn Tài", "SV018"),
      StudentModel("Phạm Thị Tuyết", "SV019"),
      StudentModel("Lê Văn Vũ", "SV020")
    )

    studentAdapter = StudentAdapter(
      students,
      onEditClick = { position, student -> showEditDialog(position, student) },
      onDeleteClick = { position, student -> showDeleteConfirmationDialog(position, student) }
    )

    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    //Add
    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      val dialogView = layoutInflater.inflate(R.layout.dialog_add_edit_student, null)

      val dialog = AlertDialog.Builder(this)
        .setTitle("Add New Student")
        .setView(dialogView)
        .setPositiveButton("Add") { _, _ ->
          val name = dialogView.findViewById<EditText>(R.id.edit_student_name).text.toString()
          val id = dialogView.findViewById<EditText>(R.id.edit_student_id).text.toString()

          if (name.isNotEmpty() && id.isNotEmpty()) {
            students.add(StudentModel(name, id))
            studentAdapter.notifyItemInserted(students.size - 1)
          } else {
            Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show()
          }
        }
        .setNegativeButton("Cancel", null)
        .create()

      dialog.show()
    }
  }

  //Edit
  private fun showEditDialog(position: Int, student: StudentModel) {
    val dialogView = layoutInflater.inflate(R.layout.dialog_add_edit_student, null)

    dialogView.findViewById<EditText>(R.id.edit_student_name).setText(student.studentName)
    dialogView.findViewById<EditText>(R.id.edit_student_id).setText(student.studentId)

    val dialog = AlertDialog.Builder(this)
      .setTitle("Edit Student")
      .setView(dialogView)
      .setPositiveButton("Update") { _, _ ->
        val updatedName = dialogView.findViewById<EditText>(R.id.edit_student_name).text.toString()
        val updatedId = dialogView.findViewById<EditText>(R.id.edit_student_id).text.toString()

        if (updatedName.isNotEmpty() && updatedId.isNotEmpty()) {
          val updatedStudent = StudentModel(updatedName, updatedId)
          studentAdapter.updateStudent(position, updatedStudent)
        } else {
          Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show()
        }
      }
      .setNegativeButton("Cancel", null)
      .create()

    dialog.show()
  }

  //Delete
  private fun showDeleteConfirmationDialog(position: Int, student: StudentModel) {
    AlertDialog.Builder(this)
      .setTitle("Delete Student")
      .setMessage("Are you sure you want to delete ${student.studentName}?")
      .setPositiveButton("Yes") { _, _ ->
        val removedStudent = student
        studentAdapter.removeStudent(position)

        Snackbar.make(
          findViewById(R.id.recycler_view_students),
          "${removedStudent.studentName} deleted",
          Snackbar.LENGTH_LONG
        ).setAction("Undo") {
          studentAdapter.restoreStudent(position, removedStudent)
        }.show()
      }
      .setNegativeButton("No", null)
      .create()
      .show()
  }
}

