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

import java.io.IOException;  // Para cargar el archivo de configuración
import java.io.InputStream;  // Para usar SqlSessionFactory

import org.apache.ibatis.io.Resources;  // Para usar SqlSession
import org.apache.ibatis.session.SqlSession;  // Para construir la fábrica de sesiones
import org.apache.ibatis.session.SqlSessionFactory;  // Para manejar excepciones de entrada/salida
import org.apache.ibatis.session.SqlSessionFactoryBuilder;  // Para manejar InputStream

import edu.unisabana.dyas.sampleprj.dao.mybatis.mappers.ClienteMapper;  // Para usar el Mapper de Cliente


public class MyBatisExample {

    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            try {
                // Se asegura de que 'Resources' sea importado
                InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    public static void main(String args[]) {
        // Abre la sesión de MyBatis
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();

        // Asegúrate de importar el ClienteMapper
        ClienteMapper cm = sqlss.getMapper(ClienteMapper.class);
        System.out.println(cm.consultarClientes());

        // Finaliza y cierra la sesión
        sqlss.commit();
        sqlss.close();
    }
}
