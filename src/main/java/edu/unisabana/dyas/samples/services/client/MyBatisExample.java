/*
 * Copyright (C) 2015 cesarvefe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.unisabana.dyas.samples.services.client;

import java.io.IOException;
import java.io.InputStream; 
import java.sql.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder; 

import edu.unisabana.dyas.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.unisabana.dyas.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.unisabana.dyas.samples.entities.Cliente;
import edu.unisabana.dyas.samples.entities.Item;
import edu.unisabana.dyas.samples.entities.TipoItem;


public class MyBatisExample {

    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            try {
                InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    public static void main(String args[]) {
  
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        ClienteMapper cm = sqlss.getMapper(ClienteMapper.class);
        System.out.println("PARTE I:");
        System.out.println(cm.consultarClientes());

        System.out.println("\nPARTE II:");
        Cliente cliente = cm.consultarCliente(123456789); 
        if (cliente != null) {
            System.out.println("\nCliente encontrado:");
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Telefono: " + cliente.getTelefono());
            System.out.println("Direccion: " + cliente.getDireccion());
            System.out.println("Email: " + cliente.getEmail());
        } else {
            System.out.println("Cliente no encontrado.");
        }

        ItemMapper itemMapper = sqlss.getMapper(ItemMapper.class);

        System.out.println("\nInsertar nuevo item:");
        Item existingItem = itemMapper.consultarItem(4);
        if (existingItem == null) {
            Item newItem = new Item();
            newItem.setId(4);
            newItem.setNombre("Nuevo Item");
            newItem.setDescripcion("Descripción del nuevo item");
            newItem.setFechalanzamiento(Date.valueOf("2005-01-23"));
            newItem.setTarifaxdia(1000);
            newItem.setFormatorenta("Diario");
            newItem.setGenero("Electrodoméstico");
            TipoItem tipoItem = new TipoItem(1, "Electrónico");
            newItem.setTipo(tipoItem);
            itemMapper.insertarItem(newItem);
            System.out.println("\nNuevo item insertado.");
        } else {
            System.out.println("\nEl item con ID 4 ya existe, no se insertará.");
        }
        
        System.out.println("\nLista de los items:");
        System.out.println(itemMapper.consultarItems());

        Item itemConsultado = itemMapper.consultarItem(4);
        if (itemConsultado != null) {
            System.out.println("\nItem nuevo encontrado:");
            System.out.println("ID: " + itemConsultado.getId());
            System.out.println("Nombre: " + itemConsultado.getNombre());
            System.out.println("Descripción: " + itemConsultado.getDescripcion());
            System.out.println("Fecha Lanzamiento: " + itemConsultado.getFechalanzamiento());
            System.out.println("Tarifa por día: " + itemConsultado.getTarifaxdia());
            System.out.println("Formato renta: " + itemConsultado.getFormatorenta());
            System.out.println("Género: " + itemConsultado.getGenero());
            System.out.println("Tipo: " + itemConsultado.getTipo().getID() + ": " + itemConsultado.getTipo().getDescripcion());
        } else {
            System.out.println("Item no encontrado.");
        }

        // Asignar el item 4 a un cliente (por ejemplo, cliente con documento 123456789)
        System.out.println("\nRentar el item:");
        cm.agregarItemRentadoACliente(123456789, 4, Date.valueOf("2024-03-25"), Date.valueOf("2024-04-01"));
        System.out.println("Item 4 asignado al cliente 123456789.");
        
        sqlss.commit();
        sqlss.close();
    }
}