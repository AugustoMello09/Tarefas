FROM node:20 as builder
WORKDIR /app
COPY . . 
RUN npm install
RUN npm run build -- --configuration=production

FROM nginx:alpine
COPY --from=builder /app/dist/tarefas-frontend usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
COPY mime.types /etc/nginx/mime.types
EXPOSE 4200
CMD [ "nginx", "-g", "daemon off;" ]