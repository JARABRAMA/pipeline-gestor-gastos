function fn() {
 var port = karate.properties['server.port'] || '8080';
  return {
    baseUrl: 'http://localhost:' + port,
    categoriaA: 'COMIDA',   
    categoriaB: 'TRANSPORTE'
  };
}