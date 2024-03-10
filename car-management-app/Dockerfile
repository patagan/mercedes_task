# Stage 1: Build the Angular application
FROM node:14 as builder

WORKDIR /app

COPY package*.json ./

RUN npm install -g @angular/cli@latest
RUN npm install

COPY . .

RUN ng build --prod

# Stage 2: Serve the Angular application using NGINX
FROM nginx:alpine

COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
