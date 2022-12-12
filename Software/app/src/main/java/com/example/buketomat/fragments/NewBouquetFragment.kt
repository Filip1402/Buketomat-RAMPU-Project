package com.example.buketomat.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buketomat.MainActivity
import com.example.buketomat.R
import com.example.buketomat.adapters.FlowerAdapter
import com.example.buketomat.adapters.OrdersAdapter
import com.example.buketomat.backgroundworkers.FlowersSync
import com.example.buketomat.backgroundworkers.NetworkService
import com.example.buketomat.models.Flower
import com.example.buketomat.models.Order

class NewBouquetFragment : Fragment(), FlowersSync {

    private lateinit var rvFlowers : RecyclerView
    lateinit var btnDodajAutomatski : Button
    lateinit var btnNapraviBuket : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_bouquet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnDodajAutomatski = view.findViewById(R.id.btnDodajAutomatski)
        btnNapraviBuket = view.findViewById(R.id.btnNapraviBuket)

        btnDodajAutomatski.setOnClickListener{
            val randomBroj = (0..10).random()
            Toast.makeText(context, "Random buket ID: " + randomBroj, Toast.LENGTH_LONG).show()
            // TODO smisli kak bu se ovo
        }

        btnNapraviBuket.setOnClickListener {
            val itemCount = rvFlowers.adapter?.itemCount
            for (i in 0 until itemCount!!) {
                val holder = rvFlowers.findViewHolderForAdapterPosition(i)
                if (holder != null) {
                    val flowerNameView = holder.itemView.findViewById<View>(R.id.tv_flower_name) as TextView
                    val flowerPriceView = holder.itemView.findViewById<View>(R.id.tv_flower_price) as TextView
                    val flowerKolicinaView = holder.itemView.findViewById<View>(R.id.etKolicina) as TextView
                    val flowerId = i+1

                    if (flowerKolicinaView.text.toString().toInt() > 0 ) {
                        Toast.makeText(context, "Ime: " + flowerNameView.text.toString() + " id: " + flowerId + " kolicina: " + flowerKolicinaView.text.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        var activity = activity as MainActivity
        //Log.i("User",activity.user.id.toString())
        NetworkService.getFlowers(this,requireContext())
    }

    override fun AddFlowersToList(result: MutableList<Flower>) {
        rvFlowers = requireView().findViewById(R.id.rvFlowers)
        //val orderAdapter = OrdersAdapter(MockDataLoader.getDemoDataOrders())
        val flowerAdapter = FlowerAdapter(result as ArrayList<Flower>)
        rvFlowers.layoutManager = LinearLayoutManager(requireView().context)
        rvFlowers.adapter = flowerAdapter
    }

}