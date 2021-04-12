### STAGE 1: BUILD ###
FROM node:15.11.0-alpine AS build

COPY package.json package-lock.json ./

RUN npm ci
RUN mkdir /app
RUN mv ./node_modules ./app

WORKDIR /app

COPY . .

RUN npm run build -- --prod --output-path=dist

### STAGE 2: SETUP ###
FROM nginx:1.14.1-alpine

COPY --from=build /app/dist /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]

