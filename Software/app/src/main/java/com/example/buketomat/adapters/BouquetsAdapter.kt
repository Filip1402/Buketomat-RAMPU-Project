package com.example.buketomat.adapters

import com.example.buketomat.models.Bouquet
import com.example.buketomat.models.OrderBouquet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buketomat.R
import com.example.buketomat.backgroundworkers.BouquetClickListener

import com.squareup.picasso.Picasso

class BouquetsAdapter(private val bouquetsList: ArrayList<Bouquet>, private val callback: BouquetClickListener ) :
    RecyclerView.Adapter<BouquetsAdapter.BouquetViewHolder>() {


    inner class BouquetViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val bouquetName: TextView
        private val bouquetDescription: TextView
        private val bouquetPrice: TextView
        private var bouquetImage: ImageView
        private var btnAddItemToOrder: Button

        init {
            bouquetName = view.findViewById(R.id.tv_bouquetName)
            bouquetDescription = view.findViewById(R.id.tv_bouquetDescription)
            bouquetPrice = view.findViewById(R.id.tv_bouquetPrice)
            bouquetImage = view.findViewById(R.id.image_view)
            btnAddItemToOrder = view.findViewById(R.id.btnAddToOrder)
        }

        fun bind(bouquet: Bouquet) {
            bouquetName.text = bouquet.Name.toString()
            bouquetDescription.text = bouquet.Description.toString()
            bouquetPrice.text = bouquet.Price.toString() + " EUR"
            //bouquet.Picture="https://images.pexels.com/photos/56866/garden-rose-red-pink-56866.jpeg?cs=srgb&dl=pexels-pixabay-56866.jpg&fm=jpg"

            // koristenje picasso libraryja za prikazivanje slike pomocu urla
            Picasso.with(bouquetImage.context).load(bouquet.Picture).into(bouquetImage)

            //creating click event listener that will send selected bouquet to Main activity
            btnAddItemToOrder.setOnClickListener{
                //sending data to main activity is done using interface that implemented in main activity because we cant get reference to activity from this class

                callback.addBouquetToOrder(OrderBouquet(bouquet));
            }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BouquetViewHolder {
        val bouquetsView =
            LayoutInflater.from(parent.context).inflate(R.layout.bouquet_list_item, parent, false)
        return BouquetViewHolder(bouquetsView)
    }

    override fun onBindViewHolder(holder: BouquetViewHolder, position: Int) {
        holder.bind(bouquetsList[position])
    }

    override fun getItemCount(): Int {
        return bouquetsList.size
    }
}