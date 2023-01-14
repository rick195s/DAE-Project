# MOCK API

Usada para simular dados externos.

Policies:

<img src="img/policies.png" title="" alt="" width="305">

Insurers:

<img src="img/insurers.jpg" title="" alt="insurers.jpg" width="312">

Insurer_repair_shops:

<img title="" src="img/insurer_repair_shops.png" alt="" width="331">

Policy_type_details:

<img title="" src="img/policy_type_details.png" alt="policy_type_details.png" width="311">

Repair_shops:

<img src="img/repair_shops.png" title="" alt="repair_shops.png" width="317">

Policy_objects:

<img src="img/policy_object.png" title="" alt="policy_object.png" width="322">

# Wildfly + PostgreSQL with Docker & Docker Compose

## Setup

1. Copy the .env.example file to a .env file:
   
   ```bash
   $ cp .env.example .env
   ```

Make the adjustments you need, to match your needs.

2. Start the containers
   
   ```bash
   $ docker compose up -d
   ```

## Stoping / Pausing / Restarting the containers

Please, check the documentation for more useful command tips [here](https://docs.docker.com/compose/reference/)

## How to clear everything from your Computer

To stop and remove all the containers, images, volumes and network, run:

```bash
$ docker compose down --rmi all --volumes
```

## Alternative: remove only local (custom) images:

```bash
# Only removes the custom wildfly image, if you need to rebuild it again.
$ docker compose down --rmi local
```
