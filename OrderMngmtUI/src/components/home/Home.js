import React, { useEffect, useState } from 'react';
import { Grid, Container, Segment, Dimmer, Loader, Image } from 'semantic-ui-react';
import image1 from '../../assets/imagen1.png';
import image2 from '../../assets/imagen2.png';

function Home() {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // Simulate a delay to demonstrate loading state
    setTimeout(() => {
      setIsLoading(false);
    }, 1000);
  }, []);

  const noticias = [
    {
      image: image1,
      title: 'Noticias recientes'
    },
    {
      image: image2,
      title: 'Noticias recientes'
    }
  ];

  if (isLoading) {
    return (
      <Segment basic style={{ marginTop: window.innerHeight / 2 }}>
        <Dimmer active inverted>
          <Loader inverted size='huge'>Loading</Loader>
        </Dimmer>
      </Segment>
    );
  }

  return (
    <Container text>
      {noticias.map((noticia, index) => (
        <div key={index} style={{ marginTop: '2em', textAlign: 'center' }}>
          <Image src={noticia.image} alt={noticia.title} />
          <h3>{noticia.title}</h3>
        </div>
      ))}
    </Container>
  );
}

export default Home;
