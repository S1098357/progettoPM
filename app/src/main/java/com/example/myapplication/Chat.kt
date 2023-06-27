/*package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class Chat : AppCompatActivity() {

    private lateinit var userRecyclerView:RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter

    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbref:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_contatti)

        mAuth=FirebaseAuth.getInstance()
        mDbref=FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/").getReference()

        userList=ArrayList()
        adapter=UserAdapter(this,userList)

        userRecyclerView=findViewById(R.id.user_recycler)
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.adapter=adapter

        mDbref.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children){

                    val currentUser=postSnapshot.getValue(User::class.java)

                    if (mAuth.currentUser?.uid!=currentUser?.uid) {
                        userList.add(currentUser!!)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout){
            mAuth.signOut()
            val intent= Intent(this@Chat,Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}*/

package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class Chat : Fragment() {

    private lateinit var userRecyclerView:RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter

    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbref:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root= inflater.inflate(R.layout.lista_contatti, container, false)
        userRecyclerView=root.findViewById(R.id.user_recycler)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth=FirebaseAuth.getInstance()
        mDbref=FirebaseDatabase.getInstance("https://unifind-53d53-default-rtdb.europe-west1.firebasedatabase.app/").getReference()

        userList=ArrayList()
        adapter=UserAdapter(requireContext(),userList)

        userRecyclerView.layoutManager=LinearLayoutManager(context)
        userRecyclerView.adapter=adapter

        mDbref.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children){

                    val currentUser=postSnapshot.getValue(User::class.java)

                    if (mAuth.currentUser?.uid!=currentUser?.uid) {
                        userList.add(currentUser!!)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}