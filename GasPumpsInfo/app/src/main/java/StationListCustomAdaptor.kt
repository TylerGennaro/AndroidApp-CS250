import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.monmouth.cs250.student.gaspumpsinfo.Pump
import edu.monmouth.cs250.student.gaspumpsinfo.R

class StationListCustomAdaptor (private val context: Context) : RecyclerView.Adapter <CustomViewHolder> (){
    private val stationList = Pump.getAllPumps()

    // number of items in RecyclerView

    override fun getItemCount(): Int {
        val size = stationList.count()
        return size
    }

    // create a viewHolder for the view

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val stationItemLayout = LayoutInflater.from(parent.context)
        val stationItemView = stationItemLayout.inflate(R.layout.stationlist_view, parent, false)
        return CustomViewHolder(stationItemView)
    }

    // get the data for viewHolder for CustomViewHolder

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val student = stationList[position]
        holder.bind(student, context, null)
    }
}

class CustomViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {

    var nameTextView: TextView = itemView.findViewById(R.id.station_name)
    var townshipTextView: TextView = itemView.findViewById(R.id.station_township)
    var idTextView: TextView = itemView.findViewById(R.id.station_id)

    fun bind (pump: Pump, context: Context, itemViewListener: AdapterView.OnItemClickListener?) {

        nameTextView.text = pump.name
        townshipTextView.text = pump.township
        idTextView.text = pump.pumpID.toString()
    }
}