package com.bignerdranch.android.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CrimeListFragment"
class CrimeListFragment : Fragment() {

    /*호스팅 액티비티에서 구현할 인터페이스*/
    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this).get(CrimeListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { crimes ->
                crimes.let {
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }

            }
        )
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    private inner class CrimeHolder(view: View)
        :RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var crime: Crime

        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView? = itemView.findViewById(R.id.crime_solved)
        private val policeButton: Button? = itemView.findViewById(R.id.call_button) ?: null //경찰에 연락 버튼(챌린지9)

        init {
            itemView.setOnClickListener(this)
            //경찰에 연락 버튼 기능(챌린지 9)
            policeButton?.setOnClickListener {
                Toast.makeText(context, "경찰에 연락했습니다!", Toast.LENGTH_SHORT).show()
            }

        }

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            // dateTextView.text = this.crime.date.toString() // 기존 날짜 출력 형식
            dateTextView.text = SimpleDateFormat("EEEE, MMMM dd, yyyy  ", Locale.getDefault()).format(this.crime.date) //챌린지 10
            solvedImageView?.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(v: View?) {
            callbacks?.onCrimeSelected(crime.id)
        }

    }

    private inner class CrimeAdapter(var crimes: List<Crime>)
        : RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            return if (viewType == 0) { //경찰에 연락 버튼이 없는 뷰
                val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
                CrimeHolder(view)
            } else { //경찰에 연락이 있는 뷰
                val view = layoutInflater.inflate(R.layout.list_item_crime_require_police, parent, false)
                CrimeHolder(view)
            }
        }

        override fun getItemCount() = crimes.size

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }
        //뷰 타입 리턴(챌린지 9)
//        override fun getItemViewType(position: Int): Int {
//            val crime = crimes[position]
//            return crime.requiresPolice
//        }
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}