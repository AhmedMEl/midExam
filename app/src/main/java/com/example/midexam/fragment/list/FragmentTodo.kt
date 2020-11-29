package com.example.midexam.fragment.list


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
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


class FragmentTodo : Fragment() {
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var taskRecyclerView: RecyclerView
    private var adapter: ListAdapter? = ListAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.todo_fragment,container,false)

        taskRecyclerView = view.findViewById(R.id.todo_recyclerView) as RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = adapter
        mTaskViewModel=ViewModelProvider(this).get(TaskViewModel::class.java)
        mTaskViewModel.readAllData.observe(viewLifecycleOwner, Observer {task->
            adapter?.setData(task)
        }
        )

        floatingActionButton = view.findViewById(R.id.floatingActionButton_id)
        floatingActionButton.setOnClickListener {

            val dailog=AlertDialog.Builder(activity)
            val view=layoutInflater.inflate(R.layout.dailog1,null)
            val titleDoTo=view.findViewById<EditText>(R.id.edit_add_title)
            val detailsDoTo=view.findViewById<EditText>(R.id.edit_add_details)
            val dateDoTo=view.findViewById<EditText>(R.id.edit_add_date)
            val btnAdd=view.findViewById<Button>(R.id.btn_add)

            val titleT=titleDoTo.text.toString()
            val detailsT=detailsDoTo.text.toString()
            val dateT=dateDoTo.text.toString()

            btnAdd.setOnClickListener {
                if (titleDoTo.text.isNotEmpty() && detailsDoTo.text.isNotEmpty() && dateDoTo.text.isNotEmpty()){
                    val task=Task(0,titleT,detailsT,dateT,0)
                    mTaskViewModel.addTask(task)
                    Toast.makeText(activity, "Add Task ", Toast.LENGTH_SHORT).show()
                    titleDoTo.text.clear()
                    detailsDoTo.text.clear()
                    dateDoTo.text.clear()

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

    private inner class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>(){
        private var taskList= emptyList<Task>()

        inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
            private lateinit var task: Task
            val titleTextView: TextView = itemView.findViewById(R.id.title_todo)
            val detailsTextView: TextView = itemView.findViewById(R.id.date_todo)
            val dateTextView: TextView = itemView.findViewById(R.id.details_todo)
            fun bind(task: Task) {
                this.task = task
                    titleTextView.text = this.task.titleTask
                    detailsTextView.text=this.task.detailsTask
                    dateTextView.text = this.task.dateTask
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = layoutInflater.inflate(R.layout.item_todo, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
                val currentItem=taskList[position]
                holder.bind(currentItem)

        }

        override fun getItemCount(): Int {
            return taskList.size
        }
        fun setData(task:List<Task>){
            this.taskList=task
            notifyDataSetChanged()
        }
    }

}