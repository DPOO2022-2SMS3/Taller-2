package Modelo;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Restaurante 
{
	private Pedido pedidoAct;
	private ProductoAjustado productoAct;
	private int contadorPedidos;
	
	private ArrayList<Ingrediente> ingredientesList;
	private ArrayList<ProductoMenu> productosList;
	private ArrayList<Combo> combosList;
	
	private Map<Integer, Pedido> pedidos;
	
	
	public Restaurante() 
	{
		this.contadorPedidos = 0;
		ProductoMenu temp = new ProductoMenu(null, 0, 0);
		this.productoAct = new ProductoAjustado(temp);
		this.pedidos = new HashMap<>();
	}
	
	
	
	public void iniciarPedido(String nombreCliente, String direccionCliente) 
    {
		contadorPedidos = contadorPedidos + 1;
		Pedido pedidoAct = new Pedido(nombreCliente, direccionCliente, contadorPedidos);
		setPedidoAct(pedidoAct);
    }

    public void cerrarYGuardarPedido() throws IOException 
    {
    	pedidoAct.guardarFactura();
    	this.pedidos.put(pedidoAct.getIdPedido(), pedidoAct);
    	pedidoAct = null;
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
    	
    	for (int i = ingredientes.size()-1; i>= 0 && elIngrediente == null; i--)
    	{	
	    	 Ingrediente unIngrediente = ingredientes.get(i);
	    	 if (unIngrediente.getNombre() == nombreIngrediente)
	    	 {
	    		 elIngrediente = unIngrediente;
    	     }		
    	}

    	 return elIngrediente;
	}
    
    public static ProductoMenu getMenuBase(ArrayList<ProductoMenu> productos, String nombreProducto)
    {
    	ProductoMenu elProducto = null;
    	
    	for (int i = productos.size()-1; i>= 0 && elProducto == null; i--)
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
    	
    	for (int i = combos.size()-1; i>= 0 && elCombo == null; i--)
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
    	String archivoMenu = "Data/menu.txt";
    	String archivoIngredientes = "Data/ingredientes.txt";
    	String archivoCombos = "Data/combos.txt";
    	
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
    		
    		ProductoMenu ElProducto = getMenuBase(productos, nombreProducto);
    		
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
    		int descuento = Integer.parseInt(descuento1);
    		
    		Combo ElCombo = getCombos(combos, nombreCombo);
    		
    		if (ElCombo == null)
    		{
    			ArrayList<ProductoMenu> itemsCombo = new ArrayList<>();
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

	public Pedido getPedidoEnCurso()
	{
		return pedidoAct;
	}
	
	public String getPedidoxID(int idPedido)
	{
		Pedido consulta = this.pedidos.get(idPedido);
		String info =consulta.generarTextoFactura();
		return info;
	}

	public void setPedidoAct(Pedido pedidoAct) {
		this.pedidoAct = pedidoAct;
	}
	
	public void ejecutarAgregarProducto(int idProducto)
	{
		
		Producto producto = getProducto(idProducto);
		pedidoAct.agregarProducto(producto);
	}
	
	public void ejecutarAgregarProductoAjustado()
	{
		pedidoAct.agregarProducto(productoAct);
		ProductoMenu temp = new ProductoMenu(null, 0, 0);
		this.productoAct = new ProductoAjustado(temp);
	}
	
	public Producto getProducto(int idProducto)
	{
		Producto producto = null;
		if (idProducto > 100 && idProducto < 123)
		{
			producto = getProductoMenuxID(idProducto);
		}
		else if (idProducto > 300 && idProducto < 304)
		{
			producto = getComboxID(idProducto);
		}
		return producto;
		
	}
	
	public ProductoMenu getProductoMenuxID(int idProducto)
	{
		ProductoMenu producto = null;

		for (int i = this.productosList.size() - 1; i >= 0 && producto == null; i--)
    	{
   			ProductoMenu unProducto = productosList.get(i);
   			if (unProducto.getId() == idProducto)
   			{
   				producto = unProducto;
   			}
    	}
		
		return producto;
	}
	
	public Combo getComboxID(int idProducto)
	{
		Combo producto = null;

		for (int i = this.combosList.size() - 1; i >= 0 && producto == null; i--)
    	{
   			Combo unCombo = combosList.get(i);
   			if (unCombo.getId() == idProducto)
   			{
   				producto = unCombo;
   			}
    	}

		return producto;
	}
	
	public Ingrediente getIngredientexID(int idProducto)
	{
		Ingrediente producto = null;
		if (idProducto > 200 && idProducto < 216)
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
		return producto;
	}
	
	public void ejecutarPrepararProductoAjustado(int idProducto, int x4bool, int idIngrediente)
	{
		if (productoAct.getId() != idProducto)
		{
			ProductoMenu productoBase = getProductoMenuxID(idProducto);
			productoAct = new ProductoAjustado(productoBase);
		}
		Ingrediente ingrediente = getIngredientexID(idIngrediente);
		
		if (x4bool == 1)//Agrega
		{
			ArrayList<Ingrediente> agregadosList = productoAct.getIngredientesAgregados();
			agregadosList.add(ingrediente);
		}
		else if (x4bool == 2)//Elimina
		{
			ArrayList<Ingrediente> eliminadosList = productoAct.getIngredientesEliminados();
			eliminadosList.add(ingrediente);
		}
		
	}
	

}
