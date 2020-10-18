package br.emerson.pi4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var toggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.toggle = ActionBarDrawerToggle( this, drawerLayout, R.string.open, R.string.close )

        this.toggle?.let {
            drawerLayout.addDrawerListener(it)
            it.syncState()
        }

        this.carregarMainFragment()

        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()

            if ( it.itemId == R.id.principal )
            {
                this.carregarMainFragment()
                true
            }

            if ( it.itemId == R.id.jogos )
            {
                val fra2 = ProdutoListaFragment.newInstance("main", getString(R.string.jogos), 0)
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, fra2).commit()
            }

            if ( it.itemId == R.id.plataformas )
            {
                val fra3 = CategoriaFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, fra3).commit()
            }

            if ( it.itemId == R.id.lancamentos )
            {
                val frag = ProdutoListaFragment.newInstance( "lancamentos", getString(R.string.lancamentos), 0 )
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
            }

            if ( it.itemId == R.id.maisvendidos )
            {
                val frag = ProdutoListaFragment.newInstance( "maisvendidos", getString(R.string.maisVendidos), 0 )
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
            }

            if ( it.itemId == R.id.carrinho )
            {
                this.carregarCarrinhoFragment()
            }

            if ( it.itemId == R.id.conta )
            {
                this.carregarContaFragment()
            }

            if ( it.itemId == R.id.sobre )
            {
                val frag = SobreFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
            }

            false
        }
    }

    private fun carregarMainFragment()
    {
        val frag = MainFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
    }

    private fun carregarCarrinhoFragment()
    {
        val frag = CarrinhoFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
    }

    private fun carregarContaFragment()
    {
        val frag = PerfilFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menucima, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Opções do menu de cima
        if ( item.itemId == R.id.menuCarrinho )
        {
            this.carregarCarrinhoFragment()
        }

        if ( item.itemId == R.id.menuConta )
        {
            this.carregarContaFragment()
        }

        // Ações das opções da navigationview
        toggle?.let {
            return it.onOptionsItemSelected(item)
        }

        return false
    }
}