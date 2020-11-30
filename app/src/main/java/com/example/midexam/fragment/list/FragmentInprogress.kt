package com.example.midexam.fragment.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midexam.R
import com.example.midexam.data.Task
import com.example.midexam.data.TaskViewModel

class FragmentInprogress : Fragment() {
    private lateinit var taskRecyclerView: RecyclerView
    private var adapter: FragmentInprogress.ListAdapter? = ListAdapter(emptyList())
    private val mTaskViewModel: TaskViewModel by lazy {
        ViewModelProvider(this).get(TaskViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.inprogress_fragment,container,false)
        taskRecyclerView = view.findViewById(R.id.inprogress_recyclerView) as RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = adapter
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTaskViewModel.readInProgressData.observe(
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
        val titleTextView: TextView = itemView.findViewById(R.id.title_inprogress)
        val detailsTextView: TextView = itemView.findViewById(R.id.details_inprogress)
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
            val view = layoutInflater.inflate(R.layout.item_inprogress, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount() = task.size

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val doneBtn: Button = holder.itemView.findViewById(R.id.end_id)
            val currentItem = task[position]
            doneBtn.setOnClickListener {
                val updateStateTask=Task(currentItem.id,currentItem.titleTask,currentItem.detailsTask,currentItem.dateTask,2)
                mTaskViewModel.updateTask(updateStateTask)
            }
            holder.bind(currentItem)
        }
    }

}