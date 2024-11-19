package vn.edu.hust.studentman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
  private val students: MutableList<StudentModel>,
  private val onEditClick: (Int, StudentModel) -> Unit,
  private val onDeleteClick: (Int, StudentModel) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

  class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context)
      .inflate(R.layout.layout_student_item, parent, false)
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId
    holder.imageEdit.setOnClickListener {
      onEditClick(position, student)
    }
    holder.imageRemove.setOnClickListener {
      onDeleteClick(position, student)
    }
  }

  fun updateStudent(position: Int, updatedStudent: StudentModel) {
    students[position] = updatedStudent
    notifyItemChanged(position)
  }

  fun removeStudent(position: Int) {
    students.removeAt(position)
    notifyItemRemoved(position)
    notifyItemRangeChanged(position, students.size)
  }

  fun restoreStudent(position: Int, student: StudentModel) {
    students.add(position, student)
    notifyItemInserted(position)
  }
}

