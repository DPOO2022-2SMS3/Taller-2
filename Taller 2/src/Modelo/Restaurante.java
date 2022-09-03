package Modelo;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Modelo.Ingrediente;
import Modelo.Pedido;

public class Restaurante 
{
	private Pedido pedidoAct;
	
	private ArrayList<Ingrediente> ingredientesList;
	private ArrayList<ProductoMenu> productosList;
	private ArrayList<Combo> combosList;
	
	public Restaurante() 
	{
	}
	
	
	
	public void iniciarPedido(String nombreCliente, String direccionCliente) 
    {
		Pedido pedidoAct = new Pedido(nombreCliente, direccionCliente);
		setPedidoAct(pedidoAct);
		
		
    }

    public void cerrarYGuardarPedido() 
    {

    }

    public Pedido getPedidoEnCurso() 
    {
        return null;
    }

    public ArrayList<Producto> getMenuBase()
    {
		return null;

    }

    /**
     * Busca un ingrediente en la lista de ingredientes por el nombre
     * 
     * Si no encuentra el ingrediente retorna null
     * 
     * @param nombreIngrediente
     * @param ingredientes
     * @param precio 
     */
    

    public static Ingrediente getIngredientes(ArrayList<Ingrediente> ingredientes, String nombreIngrediente)
    {
    	Ingrediente elIngrediente = null;
    	
    	for (int i = ingredientes.size(); i>= 0 && elIngrediente == null; i--)
    	{
	    	 Ingrediente unIngrediente = ingredientes.get(i);
	    	 if (unIngrediente.getNombre() == nombreIngrediente)
	    	 {
	    		 elIngrediente = unIngrediente;
    	     }
    	 
    	}
    	 return elIngrediente;
	}
    
    public static ProductoMenu getProductos(ArrayList<ProductoMenu> productos, String nombreProducto)
    {
    	ProductoMenu elProducto = null;
    	
    	for (int i = productos.size(); i>= 0 && elProducto == null; i--)
    	{
	    	 ProductoMenu unProducto = productos.get(i);
	    	 if (unProducto.getNombre() == nombreProducto)
	    	 {
	    		 elProducto = unProducto;
    	     }
    	 
    	}
    	 return elProducto;
	}
    
    public static Combo getCombos(ArrayList<Combo> combos, String nombreCombo)
    {
    	Combo elCombo = null;
    	
    	for (int i = combos.size(); i>= 0 && elCombo == null; i--)
    	{
	    	 Combo unCombo = combos.get(i);
	    	 if (unCombo.getNombre() == nombreCombo)
	    	 {
	    		 elCombo = unCombo;
    	     }
    	 
    	}
    	 return elCombo;
	}



	public void cargarInformacionRestaurante() throws FileNotFoundException, IOException 
    {
    	String archivoMenu = "menu.txt";
    	String archivoIngredientes = "ingredientes.txt";
    	String archivoCombos = "combos.txt";
    	
		cargarIngredientes(archivoIngredientes);    	
    	cargarMenu(archivoMenu);
		cargarCombos(archivoCombos, productosList);
		
    }

    private void cargarIngredientes(String archivoIngredientes) throws IOException
    {
    	BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));
    	String linea = br.readLine();
    	ArrayList<Ingrediente> ingredientes = new ArrayList<>();
    	int id = 200;
    	
    	while (linea != null)
    	{
    		String[] partes = linea.split(";");
    		String nombreIngrediente = partes[0]; 
    		int precio = Integer.parseInt(partes[1]);
    		
    		Ingrediente ElIngrediente = getIngredientes(ingredientes, nombreIngrediente);
    		
    		if (ElIngrediente == null)
    		{
    			id = id + 1;
    			ElIngrediente = new Ingrediente(nombreIngrediente, precio, id);
    			ingredientes.add(ElIngrediente);
    		}
    		
    		linea = br.readLine();
    	}
    	
    	br.close();
    	setIngredientesList(ingredientes);    	
    }
    

    private void cargarMenu(String archivoMenu) throws FileNotFoundException, IOException
    {
    	BufferedReader br = new BufferedReader(new FileReader(archivoMenu));
    	String linea = br.readLine();
    	ArrayList<ProductoMenu> productos = new ArrayList<>();
    	int id = 100;
    	
    	while (linea != null)
    	{
    		String[] partes = linea.split(";");
    		String nombreProducto = partes[0]; 
    		int precio = Integer.parseInt(partes[1]);
    		
    		ProductoMenu ElProducto = getProductos(productos, nombreProducto);
    		
    		if (ElProducto == null)
    		{
    			id = id + 1;
    			ElProducto = new ProductoMenu(nombreProducto, precio, id);
    			productos.add(ElProducto);
    		}
    		
    		linea = br.readLine();
    	}
    	br.close();
		setProductosList(productos);
    }

    private void cargarCombos(String archivoCombos, ArrayList<ProductoMenu> productos) throws FileNotFoundException, IOException
    {
    	BufferedReader br = new BufferedReader(new FileReader(archivoCombos));
    	String linea = br.readLine();
    	ArrayList<Combo> combos = new ArrayList<>();
    	int id = 300;
    	
    	while (linea != null)
    	{
    		String[] partes = linea.split(";");
    		String nombreCombo = partes[0]; 
    		String descuento1 = partes[1];
    		descuento1 = descuento1.replace("%", "");
    		double descuento = Double.parseDouble(descuento1);
    		
    		Combo ElCombo = getCombos(combos, nombreCombo);
    		
    		if (ElCombo == null)
    		{
    			ArrayList<Producto> itemsCombo = new ArrayList<>();
        		String nombreHamburguesa = partes[2];
        		String nombrePapas = partes[3];
        		String nombreBebida = partes[4];
        		ProductoMenu hamburguesa = null;
        		ProductoMenu papas = null;
        		ProductoMenu bebida = null;
        		
        		
        		for (int i = productos.size() - 1; i >= 0 && (hamburguesa == null || papas == null || bebida == null); i--)
        		{
        			ProductoMenu unProducto = productos.get(i);
        			if (unProducto.getNombre().equals(nombreHamburguesa))
        			{
        				hamburguesa = unProducto;
        			}
        			else if (unProducto.getNombre().equals(nombrePapas))
        	    	{
        	    		papas = unProducto;
        	   		}
        			else if (unProducto.getNombre().equals(nombreBebida))
        	    	{
        	    		bebida = unProducto;
        	   		}
        		} 
        		
        		
    			id = id + 1;
    			ElCombo = new Combo(nombreCombo, descuento, id, itemsCombo);
    			combos.add(ElCombo);
    			
    			ElCombo.agregarItemACombo(hamburguesa);
        		ElCombo.agregarItemACombo(papas);
        		ElCombo.agregarItemACombo(bebida);
    		}
    		
    		linea = br.readLine();
    	}
    	br.close();
		setCombosList(combos);
    }



	public ArrayList<Ingrediente> getIngredientesList() {
		return ingredientesList;
	}



	public void setIngredientesList(ArrayList<Ingrediente> ingredientesList) {
		this.ingredientesList = ingredientesList;
	}



	public ArrayList<ProductoMenu> getProductosList() {
		return productosList;
	}



	public void setProductosList(ArrayList<ProductoMenu> productosList) {
		this.productosList = productosList;
	}



	public ArrayList<Combo> getCombosList() {
		return combosList;
	}



	public void setCombosList(ArrayList<Combo> combosList) {
		this.combosList = combosList;
	}



	public Pedido getPedidoAct() {
		return pedidoAct;
	}



	public void setPedidoAct(Pedido pedidoAct) {
		this.pedidoAct = pedidoAct;
	}
	
	public void ejecutarAgregarProducto(int idProducto)
	{
		Producto producto = null;
		if (idProducto > 100 && idProducto < 200)
		{
			for (int i = this.productosList.size() - 1; i >= 0 && producto == null; i--)
    		{
    			ProductoMenu unProducto = productosList.get(i);
    			if (unProducto.getId() == idProducto)
    			{
    				producto = unProducto;
    			}
    		}
		}
		
		else if (idProducto > 200 && idProducto < 300)
		{
			for (int i = this.ingredientesList.size() - 1; i >= 0 && producto == null; i--)
    		{
    			Ingrediente unIngrediente = ingredientesList.get(i);
    			if (unIngrediente.getId() == idProducto)
    			{
    				producto = unIngrediente;
    			}
    		}
		}
		
		else if (idProducto > 300)
		{
			for (int i = this.combosList.size() - 1; i >= 0 && producto == null; i--)
    		{
    			Combo unCombo = combosList.get(i);
    			if (unCombo.getId() == idProducto)
    			{
    				producto = unCombo;
    			}
    		}
		}

		pedidoAct.agregarProducto(producto);
	
	}

	

}
