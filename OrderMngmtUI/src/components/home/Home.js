import React, { useEffect, useState } from 'react';
import { Statistic, Icon, Grid, Container, Image, Segment, Dimmer, Loader } from 'semantic-ui-react';
import { orderApi } from '../misc/OrderApi';
import { handleLogError } from '../misc/Helpers';
import image1 from '../../assets/imagen1.png';
import image2 from '../../assets/imagen2.png';

function Home() {
  const [numberOfUsers, setNumberOfUsers] = useState(0);
  const [numberOfOrders, setNumberOfOrders] = useState(0);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    async function fetchData() {
      try {
        const responseUsers = await orderApi.numberOfUsers();
        const numberOfUsers = responseUsers.data;

        const responseOrders = await orderApi.numberOfOrders();
        const numberOfOrders = responseOrders.data;

        setNumberOfUsers(numberOfUsers);
        setNumberOfOrders(numberOfOrders);
      } catch (error) {
        handleLogError(error);
      } finally {
        setIsLoading(false);
      }
    }

    fetchData();
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
      <Grid stackable columns={2}>
        <Grid.Row>
          <Grid.Column textAlign='center'>
            <Segment color='teal'>
              <Statistic>
                <Statistic.Value><Icon name='user' color='grey' />{numberOfUsers}</Statistic.Value>
                <Statistic.Label>Users</Statistic.Label>
              </Statistic>
            </Segment>
          </Grid.Column>
          <Grid.Column textAlign='center'>
            <Segment color='teal'>
              <Statistic>
                <Statistic.Value><Icon name='laptop' color='grey' />{numberOfOrders}</Statistic.Value>
                <Statistic.Label>Orders</Statistic.Label>
              </Statistic>
            </Segment>
          </Grid.Column>
        </Grid.Row>
      </Grid>
      
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
