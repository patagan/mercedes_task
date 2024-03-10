# Stage 1: Build the Angular application
FROM node:20.11.1 as builder

WORKDIR /app

COPY package*.json ./

RUN npm install -g @angular/cli@latest
RUN npm install

COPY . .

RUN ng build

# Stage 2: Serve the Angular application using NGINX
FROM nginx:alpine

COPY --from=builder /app/dist/car-management-app/browser /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
