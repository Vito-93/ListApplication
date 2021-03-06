package it.polito.mad.es.s291595.listapplication

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import java.lang.Error

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)

        var counter = 0

        val boys = listOf<String>("Peppe", "Jhonny", "Carmelo", "Luigi", "Saro")
        val girls = listOf<String>("Peppa", "Giovanna", "Carmela", "Luigia", "Sara")

        val itemboys = (1..3).map {
            MalePerson(boys.elementAt(it-1), (Math.random()*20+18).toInt(), imageUrl ="https://randomuser.me/api/portraits/men/$it.jpg")
        }.toMutableList()

        val itemgirls = (1..3).map {
            FemalePerson(girls.elementAt(it-1), (Math.random()*20+18).toInt(), imageUrl ="https://randomuser.me/api/portraits/women/$it.jpg")
        }.toMutableList()
        //"https://randomuser.me/api/portraits/women$it.jpg"         https://randomuser.me/api/portraits/women/26.jpg2

        // mix 1 boy and 1 girl 1 boy and 1 girl...
        val people = itemboys.zip(itemgirls).flatMap{ it -> listOf(it.first, it.second)}
        val itemAdapter = ItemAdapter(people as MutableList<Item>)
        rv.adapter = itemAdapter

//        //add item
//        val add = findViewById<Button>(R.id.add)
//        add.setOnClickListener {
//            val n = items.size + 1
//            itemAdapter.add(
//                Item(
//                    "Name$n",
//                    (Math.random() * 20 + 18).toInt(),
//                    imageUrl = "https://randomuser.me/api/portraits/men/$n.jpg"
//                )
//            )
//        }
//        //delete item
//        val remove = findViewById<Button>(R.id.remove)
//        try {
//            remove.setOnClickListener {
//                counter++;
//                itemAdapter.delete()
//
//            }
//        } catch (err: Error){
//            Log.e(TAG, "Errore delete")
//        }
    }
}

open class Item(val name: String, val age: Int, val imageUrl: String){

}

class MalePerson(name: String, age: Int, imageUrl: String): Item(name, age, imageUrl){

}

class FemalePerson(name: String, age: Int, imageUrl: String): Item(name, age, imageUrl){

}


class ItemAdapter(val items: MutableList<Item>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){
    class ItemViewHolder(v: View): RecyclerView.ViewHolder(v){
        val picture = v.findViewById<ImageView>(R.id.imageView)
        val nameView = v.findViewById<TextView>(R.id.nameView)
        val ageView = v.findViewById<TextView>(R.id.ageView)
        val bottone = v.findViewById<Button>(R.id.bottone)

        fun bind(item: Item){

            picture.load(item.imageUrl)
            nameView.text = item.name
            ageView.text = item.age.toString()
            bottone.setOnClickListener { Toast.makeText(nameView.context, item.name, Toast.LENGTH_LONG).show() }
        }

        fun unbind(){
            bottone.setOnClickListener{ null }
        }

    }

    override fun onViewRecycled(holder: ItemViewHolder) {
        super.onViewRecycled(holder)
        Log.i(TAG, "unbind")
        holder.unbind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layout = if( viewType == R.layout.male_layout){
            layoutInflater.inflate(R.layout.male_layout, parent, false)
        } else{
            layoutInflater.inflate(R.layout.female_layout, parent, false)
        }

        return ItemViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size;
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position] is MalePerson)
            return R.layout.male_layout
        else
            return R.layout.female_layout
    }

//    fun add(item: Item){
//        items.add(item)
//        this.notifyItemInserted(items.size-1)
//    }
//
//    fun delete(){
//        items.removeAt(0)
//        this.notifyItemRemoved(0)
//    }
}