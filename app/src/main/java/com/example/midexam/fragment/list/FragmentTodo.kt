package com.example.midexam.fragment.list


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midexam.R
import com.example.midexam.data.Task
import com.example.midexam.data.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*


class FragmentTodo : Fragment() {

    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var taskRecyclerView: RecyclerView
    private var adapter: ListAdapter? = ListAdapter(emptyList())
    private val mTaskViewModel: TaskViewModel by lazy {
        ViewModelProvider(this).get(TaskViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.todo_fragment,container,false)
        taskRecyclerView = view.findViewById(R.id.todo_recyclerView) as RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = adapter

        floatingActionButton = view.findViewById(R.id.floatingActionButton_id)
        floatingActionButton.setOnClickListener {

            val dailog=AlertDialog.Builder(activity)
            val view=layoutInflater.inflate(R.layout.dailog1,null)
            val titleDoTo=view.findViewById<EditText>(R.id.edit_add_title)
            val detailsDoTo=view.findViewById<EditText>(R.id.edit_add_details)
            val dateDoTo=view.findViewById<TextView>(R.id.edit_add_date)
            val btnAddDate=view.findViewById<Button>(R.id.add_date)
            val btnAdd=view.findViewById<Button>(R.id.btn_add)
            btnAddDate.setOnClickListener {
                var formate=SimpleDateFormat("dd MMM yyyy",Locale.US)
                val now=Calendar.getInstance()
                val datePicker=DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener{
                    view,year,month,dayOfMonth->
                    val selectedDate=Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR,year)
                    selectedDate.set(Calendar.MONTH,month)
                    selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                    val date=formate.format(selectedDate.time)
                    dateDoTo.setText(date)
                },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
                datePicker.show()
            }

            btnAdd.setOnClickListener {
                if (titleDoTo.text.isNotEmpty() && detailsDoTo.text.isNotEmpty() && dateDoTo.text.isNotEmpty()){
                    val task=Task(0,titleDoTo.text.toString(),detailsDoTo.text.toString(),dateDoTo.text.toString(),0)
                    mTaskViewModel.addTask(task)
                    Toast.makeText(activity, "Add Task ", Toast.LENGTH_SHORT).show()
                    titleDoTo.text.clear()
                    detailsDoTo.text.clear()
//                    dateDoTo.text.clear()

                }else{
                    Toast.makeText(requireContext(), "please fill field ", Toast.LENGTH_SHORT).show()
                }
            }
            dailog.setView(view)

            dailog.setNegativeButton("Cancel"){ _: DialogInterface, _: Int->

            }
            dailog.show()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTaskViewModel.readToDoData.observe(
            viewLifecycleOwner,
            Observer {task->
                task?.let {
                    updateUI(task)
                }
        }
        )
    }

    private fun updateUI(task: List<Task>) {
        adapter = ListAdapter(task)
        taskRecyclerView.adapter = adapter
    }

    private inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        private lateinit var task: Task
        val titleTextView: TextView = itemView.findViewById(R.id.title_todo)
        val detailsTextView: TextView = itemView.findViewById(R.id.details_todo)
        val dateTextView: TextView = itemView.findViewById(R.id.date_todo)

        fun bind(task: Task) {
            this.task = task
            Log.d("elha", this.task.id.toString()+"...................")
            titleTextView.text = this.task.titleTask
            detailsTextView.text=this.task.detailsTask
            dateTextView.text = this.task.dateTask
        }
    }

    private inner class ListAdapter(var task: List<Task>) : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = layoutInflater.inflate(R.layout.item_todo, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount() = task.size

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val progressBtn: Button = holder.itemView.findViewById(R.id.progress_id)
            val updateBtn: FloatingActionButton = holder.itemView.findViewById(R.id.floating_update)
            val deleteBtn: FloatingActionButton = holder.itemView.findViewById(R.id.floating_delete)
            val currentItem = task[position]
            val date = Calendar.getInstance()
            val formate = SimpleDateFormat("dd MMM yyyy",Locale.US)
            val currentDate=formate.format(date.time)
            if (currentItem.dateTask==currentDate){
                holder.itemView.setBackgroundColor(Color.parseColor("#CCF80404"));

            }
            progressBtn.setOnClickListener {
                val updateStateTask=Task(currentItem.id,currentItem.titleTask,currentItem.detailsTask,currentItem.dateTask,1)
                mTaskViewModel.updateTask(updateStateTask)
            }

            updateBtn.setOnClickListener {
                val dailogUpdate=AlertDialog.Builder(activity)
                val view=layoutInflater.inflate(R.layout.dailog2,null)
                val titleDoToUpdate=view.findViewById<EditText>(R.id.edit_update_title)
                val detailsDoToUpdate=view.findViewById<EditText>(R.id.edit_update_details)
                val dateDoToUpdate=view.findViewById<TextView>(R.id.edit_update_date)
                val btnUpdateDate=view.findViewById<Button>(R.id.update_date)
                val btnUpdate=view.findViewById<Button>(R.id.btn_update)
                btnUpdateDate.setOnClickListener {
                    val datePicker=DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener{
                            view,year,month,dayOfMonth->
                        val selectedDate=Calendar.getInstance()
                        selectedDate.set(Calendar.YEAR,year)
                        selectedDate.set(Calendar.MONTH,month)
                        selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                        val date=formate.format(selectedDate.time)
                        dateDoToUpdate.setText(date)
                    },
                        date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH))
                    datePicker.show()
                }

                titleDoToUpdate.setText(currentItem.titleTask)
                detailsDoToUpdate.setText(currentItem.detailsTask)
                dateDoToUpdate.setText(currentItem.dateTask)
                btnUpdate.setOnClickListener {
                    if (titleDoToUpdate.text.isNotEmpty() && detailsDoToUpdate.text.isNotEmpty() && dateDoToUpdate.text.isNotEmpty()){
                        val taskUpdate=Task(currentItem.id,titleDoToUpdate.text.toString(),detailsDoToUpdate.text.toString(),dateDoToUpdate.text.toString(),currentItem.stateTask)
                        mTaskViewModel.updateTask(taskUpdate)
                        Toast.makeText(activity, "update Task ", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(requireContext(), "not update field ", Toast.LENGTH_SHORT).show()
                    }
                }
                dailogUpdate.setView(view)

                dailogUpdate.setNegativeButton("Cancel"){ _: DialogInterface, _: Int->

                }
                dailogUpdate.show()
            }

            deleteBtn.setOnClickListener{
                val builder=AlertDialog.Builder(activity)
                builder.setPositiveButton("Yes"){ _: DialogInterface, _: Int->
                    mTaskViewModel.deleteTask(currentItem.id)
                    Toast.makeText(activity, "delete Task ", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("Cancel"){ _: DialogInterface, _: Int->

                }
                builder.setTitle("Deleted")
                builder.setMessage("Are You Sure want to delete")
                builder.create().show()
            }
            holder.bind(currentItem)


        }
    }

}